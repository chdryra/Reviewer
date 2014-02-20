package com.chdryra.android.reviewer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.util.Log;

public class TextSearchFetcher extends GooglePlacesAPIFetcher {
	private static final String TAG = "TextSearchFetcher";
	
	private static final String TEXT_SEARCH = "/textsearch";
	private static final String RESULTS_TAG = "results";
	
	private String mQuery;
	
	public TextSearchFetcher(String query) {
		super(TEXT_SEARCH, RESULTS_TAG);
		mQuery = query;
	}
    
	@Override
	public String getURLString() {
		StringBuilder urlString = getURLStem();
        try {
			urlString.append("&query=" + URLEncoder.encode(mQuery, "utf8"));
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, "Problem with URL string", e);
			e.printStackTrace();
		}
        
		return urlString.toString();
	}	
}
