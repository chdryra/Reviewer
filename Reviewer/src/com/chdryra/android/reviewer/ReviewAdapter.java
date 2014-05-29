package com.chdryra.android.reviewer;

public class ReviewAdapter {

	private Review mReview;
	public ReviewAdapter(Review review) {
		mReview = review;
	}
	
	public enum RDType {
		COMMENTS,
		IMAGES,	
		FACTS,
		PROCONS,
		URLS,
		LOCATIONS
	}
	
	public RDList<? extends RData> get(RDType dataType) {
		switch (dataType) {
		case COMMENTS:
			return mReview.getComments();
		case IMAGES:
			return mReview.getImages();
		case FACTS:
			return mReview.getFacts();
		case PROCONS:
			return mReview.getProCons();
		case URLS:
			return mReview.getURLs();
		case LOCATIONS:
			return mReview.getLocations();
		default:
			return null;
		}
	}
	
}
