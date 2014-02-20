package com.chdryra.android.reviewer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.android.gms.maps.model.LatLng;

public class LocationNameAdapter extends ArrayAdapter<String> implements Filterable {
	private static final String TAG = "LocationNameAdapter";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyD1D7P2G9bfOCWfhTgX-tbza6kw2d4ZbYw";

    private static final int RADIUS = 25000;
	private ArrayList<String> mLocationSuggestions = null;
	private LatLng mLatLng;
	private String mLocationAddress;
	private PlacesAPIFetcher mPlacesFetcher;
	
	public LocationNameAdapter(Context context, int textViewResourceId, LatLng latlng) {
		super(context, textViewResourceId);
		mLatLng = latlng;
		if(mLatLng != null) {
			GetAddressTask task = new GetAddressTask(context);
			task.execute(mLatLng);
		}
	}
	
	@Override
    public int getCount() {
		if(mLocationSuggestions != null)
			return mLocationSuggestions.size();
		else
			return 0;
    }

    @Override
    public String getItem(int index) {
        return mLocationSuggestions.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                	mLocationSuggestions = PlacesAPIFetcher.fetchAutoCompleteSuggestions(constraint.toString(), mLatLng, RADIUS);
                    if(mLocationAddress != null)
                    	mLocationSuggestions.add(0, mLocationAddress);
                    filterResults.values = mLocationSuggestions;
                    filterResults.count = mLocationSuggestions.size();
                } else {
                	if(mLocationAddress != null) {
                		mLocationSuggestions = new ArrayList<String>();
                    	mLocationSuggestions.add(0, mLocationAddress);
                    	filterResults.values = mLocationSuggestions;
                    	filterResults.count = mLocationSuggestions.size();
                	}
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }};
            
        return filter;
    }

	    
    private class GetAddressTask extends AsyncTask<LatLng, Void, String> {
		  
		  Context mContext;
		  
		  public GetAddressTask(Context context) {
			  super();
			  mContext = context;
		  }
  
		  @Override
		  protected String doInBackground(LatLng... params) {
			  Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			  LatLng loc = params[0];
			  List<Address> addresses = null;
			  try {
			      addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1);
			  } catch (IOException e1) {
				  Log.e("LocationSampleActivity",
		          "IO Exception in getFromLocation()");
		  e1.printStackTrace();
		  Log.e(TAG, "IO Exception trying to get address");
		  } catch (IllegalArgumentException e2) {
		  String errorString = "Illegal arguments " +
		          Double.toString(loc.latitude) +
		          " , " +
		          Double.toString(loc.longitude) +
		          " passed to address service";
		  Log.e("LocationSampleActivity", errorString);
		  e2.printStackTrace();
		  Log.e(TAG, errorString);
	  }
	 
	  if (addresses != null && addresses.size() > 0) {
		  String addressText = formatAddress(addresses.get(0));
		  String nameFromGoogle = PlacesAPIFetcher.fetchNearestName(loc, addressText);
		  if(nameFromGoogle.length() > 0)
			  return nameFromGoogle;
		  else
			  return addressText;
	  } else {
		  String nameFromGoogle = PlacesAPIFetcher.fetchNearestName(loc, null);
		  if(nameFromGoogle.length() > 0)
			  return nameFromGoogle;
		  else
			  return null;
	  }
}
	  private String formatAddress(Address address) {
		  String addressText = String.format(
	              "%s%s%s",
	              // If there's a street address, add it
	              address.getMaxAddressLineIndex() > 0 ?
	                      address.getAddressLine(0) : "",
	              // Locality is usually a city
	              address.getLocality() != null ?
	            		  ", " + address.getLocality(): "",
	              address.getCountryName() != null ?
	            		  ", " + address.getCountryName() : "");

		  return addressText;
	  }
	  
	@Override
	protected void onPostExecute(String address) {
		super.onPostExecute(address);
		mLocationAddress = address;
	    mLocationSuggestions = new ArrayList<String>();
	    mLocationSuggestions.add(mLocationAddress);
	}
	}
}
