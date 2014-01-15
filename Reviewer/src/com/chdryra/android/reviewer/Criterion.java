package com.chdryra.android.reviewer;

public class Criterion {
	private static final String TAG = "Criterion";
	private String mName;		
	private float mRating;
	
	public Criterion() {
	}
	
	public Criterion(String name) {
		mName = name;
	}
	
	public Criterion(String name, float rating) {
		mName = name;
		mRating = rating;
	}

	public void setRating(float rating) {
		mRating = rating;
	}
	
	public float getRating() {
		return mRating;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public String getName() {
		return mName;
	}
}

