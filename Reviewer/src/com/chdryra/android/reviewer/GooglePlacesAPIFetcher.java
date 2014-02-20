package com.chdryra.android.reviewer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public abstract class GooglePlacesAPIFetcher{

	private static final String TAG = "GooglePlacesAPIFetcher";
    protected static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    protected static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyD1D7P2G9bfOCWfhTgX-tbza6kw2d4ZbYw";

    private String mSearchPath;
    private String mResultsTag;
    private JSONArray mResults;
    
    public abstract String getURLString();
    
    public GooglePlacesAPIFetcher(String searchPath, String resultsTag) {
    	mSearchPath = searchPath;
    	mResultsTag = resultsTag;
    }
    
    protected StringBuilder getURLStem() {
		StringBuilder sb = new StringBuilder(PLACES_API_BASE + mSearchPath + OUT_JSON);
        sb.append("?sensor=true");	
        sb.append("&key=" + API_KEY);
        
        return sb;
	}
    
    protected void performQuery() {
		StringBuilder jsonResults = new StringBuilder();
		HttpURLConnection conn = null;	    	    
	
		try {	    
	    	URL url = new URL(getURLString());    
	        conn = (HttpURLConnection) url.openConnection();
	        InputStreamReader in = new InputStreamReader(conn.getInputStream());
	        
	        int read;
	        char[] buff = new char[1024];
	        while ((read = in.read(buff)) != -1) {
	            jsonResults.append(buff, 0, read);
	        }
	    } catch (MalformedURLException e) {
	        Log.e(TAG, "Error processing Places API URL", e);
	    } catch (IOException e) {
	        Log.e(TAG, "Error connecting to Places API", e);
	    } finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }
	    
		JSONArray results = null;
		
		try {
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			results = jsonObj.getJSONArray(mResultsTag);
		} catch (JSONException e) {
			Log.e(TAG, "Cannot process JSON results", e);
			e.printStackTrace();
		}        
		
		mResults = results;
	}
    
    public JSONArray getResults() {
    	if(mResults == null)
    		performQuery();
    	
    	return mResults;
    }
	
}
