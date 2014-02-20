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
	private static final int RADIUS = 250;
	
    private ArrayList<String> mLocationSuggestions = null;
	private ArrayList<String> mLocationDefaultSuggestions;
	private LatLng mLatLng;
	private int mNumberDefaultSuggestions;
	
	public LocationNameAdapter(Context context, int textViewResourceId, LatLng latlng, int numberDefaultSuggestions) {
		super(context, textViewResourceId);
		mLatLng = latlng;
		mNumberDefaultSuggestions = numberDefaultSuggestions;
		
		if(mLatLng != null && mNumberDefaultSuggestions > 0) {
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
                
                if (constraint != null)
                	mLocationSuggestions = PlacesAPIFetcher.fetchAutoCompleteSuggestions(constraint.toString(), mLatLng, RADIUS);
                else if(mLocationDefaultSuggestions != null)
                    mLocationSuggestions = mLocationDefaultSuggestions;
           
                filterResults.values = mLocationSuggestions;
                filterResults.count = mLocationSuggestions.size();
                
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

	    
    private class GetAddressTask extends AsyncTask<LatLng, Void, ArrayList<String>> {
		  
		  Context mContext;
		  
		  public GetAddressTask(Context context) {
			  super();
			  mContext = context;
		  }
  
		  @Override
		  protected ArrayList<String> doInBackground(LatLng... params) {
			  LatLng loc = params[0];
					 			 
			  ArrayList<String> namesFromGoogle = PlacesAPIFetcher.fetchNearestNames(loc, mNumberDefaultSuggestions);
			  
			  if(namesFromGoogle.size() > 0)
				  return namesFromGoogle;
			  else {
				  Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			
				  List<Address> addresses = null;
				  try {
				      addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, mNumberDefaultSuggestions);
				  } catch (IOException e1) {
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
				  
			  if(addresses != null && addresses.size() > 0) {
				  ArrayList<String> addressesList = new ArrayList<String>();
				  for(int i = 0; i<addressesList.size(); ++i)
					  addressesList.add(formatAddress(addresses.get(i)));
				  return addressesList;
			  }				  
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
	protected void onPostExecute(ArrayList<String> addresses) {
		super.onPostExecute(addresses);
		mLocationDefaultSuggestions = addresses;
	    mLocationSuggestions = new ArrayList<String>(mLocationDefaultSuggestions);
	}
	}
}
