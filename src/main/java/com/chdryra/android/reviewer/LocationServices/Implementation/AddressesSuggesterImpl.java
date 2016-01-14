package com.chdryra.android.reviewer.LocationServices.Implementation;

import android.os.AsyncTask;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.AddressesProvider;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocatedPlace;
import com.chdryra.android.reviewer.LocationServices.Interfaces
        .AddressesSuggester;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .Implementation.GooglePlace;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AddressesSuggesterImpl implements AddressesSuggester {
    AddressesProvider mProvider;

    public AddressesSuggesterImpl(AddressesProvider provider) {
        mProvider = provider;
    }

    @Override
    public void fetchAddresses(LatLng latLng, int number, AddressSuggestionsListener listener) {
        new AddressFinderTask(latLng, listener).execute(number);
    }

    private class AddressFinderTask extends AsyncTask<Integer, Void, ArrayList<String>> {
        private final LatLng mLatLng;
        private final AddressSuggestionsListener mListener;

        public AddressFinderTask(LatLng latLng, AddressSuggestionsListener listener) {
            mLatLng = latLng;
            mListener = listener;
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            return mProvider.fetchAddresses(mLatLng, params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<String> addresses) {
            ArrayList<LocatedPlace> suggestions = new ArrayList<>();
            for(String address : addresses) {
                suggestions.add(new GooglePlace(mLatLng, address));
            }

            mListener.onAddressSuggestionsFound(suggestions);
        }
    }
}
