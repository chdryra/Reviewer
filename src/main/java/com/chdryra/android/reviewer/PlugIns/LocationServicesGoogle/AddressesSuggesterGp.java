package com.chdryra.android.reviewer.PlugIns.LocationServicesGoogle;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chdryra.android.mygenerallibrary.GooglePlacesApi;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Implementation.LocatedPlaceImpl;
import com.chdryra.android.reviewer.LocationServices.Interfaces.AddressesSuggester;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AddressesSuggesterGp implements AddressesSuggester {
    private final Context mContext;

    public AddressesSuggesterGp(Context context) {
        mContext = context;
    }

    @Override
    public void fetchAddresses(LocatedPlace place, int number, AddressSuggestionsListener listener) {
        new AddressFinderTask(place.getLatLng(), listener).execute(number);
    }

    private String formatAddress(Address address) {
        return String.format(
                "%s%s",
                // If there's a street address, add it
                address.getMaxAddressLineIndex() > 0 ?
                        address.getAddressLine(0) : "",
                // Locality is usually a city
                address.getLocality() != null ?
                        ", " + address.getLocality() : "");
    }

    /**
     * Finds nearest addresses given a LatLng on a separate thread using FetcherPlacesAPI. If this
     * returns nothing, tries to use Android's built in Geocoder class.
     */
    private class AddressFinderTask extends AsyncTask<Integer, Void, ArrayList<String>> {
        private final static String TAG = "AddressFinderTask";
        private final LatLng mLatLng;
        private final AddressSuggestionsListener mListener;

        public AddressFinderTask(LatLng latLng, AddressSuggestionsListener listener) {
            mLatLng = latLng;
            mListener = listener;
        }

        @Override
        @Nullable
        protected ArrayList<String> doInBackground(Integer... params) {
            Integer numberToGet = params[0];
            ArrayList<String> namesFromGoogle = new ArrayList<String>();
            if (mLatLng == null || numberToGet == 0) return namesFromGoogle;

            namesFromGoogle = GooglePlacesApi.fetchNearestNames(mLatLng, numberToGet);

            if (namesFromGoogle.size() > 0) {
                return namesFromGoogle;
            } else {
                //Try using Geocoder
                List<Address> addresses = getFromGeoCoder(numberToGet);

                if (addresses != null && addresses.size() > 0) {
                    ArrayList<String> addressesList = new ArrayList<String>();
                    for (int i = 0; i < addressesList.size(); ++i) {
                        addressesList.add(formatAddress(addresses.get(i)));
                    }

                    return addressesList;
                } else {
                    return null;
                }
            }
        }

        @Nullable
        private List<Address> getFromGeoCoder(Integer numberToGet) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(mLatLng.latitude, mLatLng.longitude,
                        numberToGet);
            } catch (IOException e1) {
                Log.e(TAG, "IOException: trying to get address using latitude " + mLatLng
                        .latitude + ", longitude " + mLatLng.longitude, e1);
                Log.i(TAG, "Address is null");
            } catch (IllegalArgumentException e2) {
                Log.e(TAG, "IllegalArgumentException: trying to get address using latitude " +
                        mLatLng.latitude + ", longitude " + mLatLng.longitude, e2);
                Log.i(TAG, "Address is null");
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(ArrayList<String> addresses) {
            super.onPostExecute(addresses);
            ArrayList<LocatedPlace> suggestions = new ArrayList<>();
            for(String address : addresses) {
                suggestions.add(new LocatedPlaceImpl(mLatLng, address));
            }
            mListener.onAddressSuggestionsFound(suggestions);
        }
    }
}
