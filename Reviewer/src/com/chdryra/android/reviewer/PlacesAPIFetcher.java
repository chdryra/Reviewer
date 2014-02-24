package com.chdryra.android.reviewer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class PlacesAPIFetcher {
    private static final String TAG = "PlacesAPIFetcher";
    private static final String DEFAULT_TYPE = "establishment";
    private static final String ALL_TYPES = GooglePlacesAPIFetcher.ALL_TYPES;
    private static final String NO_TYPES = null;
    
    public static final int NEARBY_RADIUS = 100;
    public static final int AUTOCOMPLETE_RADIUS = 250;
    
    public PlacesAPIFetcher() {
    }	
    	
    public static ArrayList<String> fetchAutoCompleteSuggestions(String query, LatLng latlng, int radius) {
    	AutoCompleteFetcher fetcher = new AutoCompleteFetcher(query, latlng, radius);
        return fetcher.getTag(AutoCompleteFetcher.Tag.DESCRIPTION);
	}
	
    public static LatLng fetchLatLng(String query) {
		TextSearchFetcher fetcher = new TextSearchFetcher(query);
        JSONArray results = fetcher.getResults();
        
        LatLng result = null;
        try {	        
	        JSONObject loc = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            result = new LatLng(loc.getDouble("lat"), loc.getDouble("lng"));
	    } catch (JSONException e) {
	        Log.e(TAG, "Cannot process JSON results", e);
	    }

	    return result;
	}
    
    public static ArrayList<String> fetchNearestNames(LatLng latlng, int number) {
    	NearbySearchFetcher.RadiusOrRank ror = NearbySearchFetcher.RadiusOrRank.RANK_BY_PROMINENCE;
    	
    	NearbySearchFetcher fetcher = new NearbySearchFetcher(latlng, ror, NEARBY_RADIUS, ALL_TYPES);
    	JSONArray resultsJSON = fetcher.getResults();
    	
        ArrayList<String> resultsList = new ArrayList<String>();
        if(resultsJSON != null) {
	        try {
	        	for (int i = 0; i < Math.min(number, resultsJSON.length()); i++)
	        		resultsList.add(resultsJSON.getJSONObject(i).getString("name"));
		    } catch (JSONException e) {
		        Log.e(TAG, "Cannot process JSON results", e);
		    }
        }
        
        return resultsList;
    }

}
