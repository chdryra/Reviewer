package com.chdryra.android.reviewer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.android.gms.maps.model.LatLng;

public class AutoCompleteFetcher extends GooglePlacesAPIFetcher {
	private static final String AUTOCOMPLETE = "/autocomplete";
	private static final String RESULTS_TAG = "predictions";
	 
	public static enum Tag{DESCRIPTION, ID, REFERENCE};
	private static final String DESCRIPTION_TAG = "description";
	private static final String ID_TAG = "id";
	private static final String REFERENCE_TAG = "reference";
	
	private String mQuery;
	private LatLng mLatLng;
	private int mRadius = 250;

	public AutoCompleteFetcher(String query, LatLng latlng, int radius) {
		super(AUTOCOMPLETE, RESULTS_TAG);
		mQuery = query;
		mLatLng = latlng;
		mRadius = radius;
	}
	
	@Override
	public String getURLString() {
		StringBuilder urlString = getURLStem();
	    
		if(mLatLng != null) 
	    	urlString.append("&location=" + mLatLng.latitude + "," + mLatLng.longitude);
	 
	    urlString.append("&radius=" + mRadius);
		
	    try {
			urlString.append("&input=" + URLEncoder.encode(mQuery, "utf8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
	
		return urlString.toString();
	}
	
	public ArrayList<String> getTag(Tag tagEnum) {
		String tag;
		if(tagEnum == Tag.DESCRIPTION)
			tag = DESCRIPTION_TAG;
		else if(tagEnum == Tag.ID)
			tag = ID_TAG;
		else if(tagEnum == Tag.REFERENCE)
			tag = REFERENCE_TAG;
		else
			return null;
		
		JSONArray predsJsonArray = getResults();
	    ArrayList<String>  resultList = new ArrayList<String>(predsJsonArray.length());      
	
		try {
			for (int i = 0; i < predsJsonArray.length(); i++)
				resultList.add(predsJsonArray.getJSONObject(i).getString(tag));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return resultList;	
	}
}
