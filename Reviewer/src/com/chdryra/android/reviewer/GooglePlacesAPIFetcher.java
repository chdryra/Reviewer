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
	
	public static String ALL_TYPES = "accounting|airport|amusement_park|aquarium|art_gallery|atm|bakery|bank|bar|beauty_salon|bicycle_store|book_store|bowling_alley|bus_station|cafe|campground|car_dealer|car_rental|car_repair|car_wash|casino|cemetery|church|city_hall|clothing_store|convenience_store|courthouse|dentist|department_store|doctor|electrician|electronics_store|embassy|establishment|finance|fire_station|florist|food|funeral_home|furniture_store|gas_station|general_contractor|grocery_or_supermarket|gym|hair_care|hardware_store|health|hindu_temple|home_goods_store|hospital|insurance_agency|jewelry_store|laundry|lawyer|library|liquor_store|local_government_office|locksmith|lodging|meal_delivery|meal_takeaway|mosque|movie_rental|movie_theater|moving_company|museum|night_club|painter|park|parking|pet_store|pharmacy|physiotherapist|place_of_worship|plumber|police|post_office|real_estate_agency|restaurant|roofing_contractor|rv_park|school|shoe_store|shopping_mall|spa|stadium|storage|store|subway_station|synagogue|taxi_stand|train_station|travel_agency|university|veterinary_care|zoo";
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
