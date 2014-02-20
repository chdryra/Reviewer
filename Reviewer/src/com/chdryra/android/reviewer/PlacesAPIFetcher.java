package com.chdryra.android.reviewer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class PlacesAPIFetcher {
    private static final String TAG = "PlacesAPIFetcher";
    public static final int RADIUS = 1000;
    
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
    
    public static String fetchNearestName(LatLng latlng, String address) {
    	String keyword = null;
    	NearbySearchFetcher.RadiusOrRank ror = NearbySearchFetcher.RadiusOrRank.RADIUS;
    	if(address != null) {
    		keyword = address;
    		ror = NearbySearchFetcher.RadiusOrRank.RANK_BY_DISTANCE;
    	}
    	
    	NearbySearchFetcher fetcher = new NearbySearchFetcher(latlng, ror, RADIUS, keyword);
    	JSONArray results = fetcher.getResults();
    	
        String result = null;
        try {	        
	        result = results.getJSONObject(0).getString("name");
	    } catch (JSONException e) {
	        Log.e(TAG, "Cannot process JSON results", e);
	    }
        
        return result;
    }

}
