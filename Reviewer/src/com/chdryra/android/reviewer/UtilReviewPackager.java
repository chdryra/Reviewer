package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.IntentObjectHolder;
	
public class UtilReviewPackager {
	private static final String REVIEW_ID = "com.chdryra.android.reviewer.review_packing_id";
	
	public static void pack(Review review, Intent addressedTo) {
		addressedTo.putExtra(REVIEW_ID, review.getID());
		IntentObjectHolder.addObject(review.getID().toString(), review);
	}

	public static Bundle pack(Review review) {
		Bundle args = new Bundle();
		args.putParcelable(REVIEW_ID, review.getID());
		IntentObjectHolder.addObject(review.getID().toString(), review);
		return args;
	}

	public static Review get(Intent addressedTo) {
		RDId reviewId = addressedTo.getParcelableExtra(REVIEW_ID);
		Review review = reviewId != null? (Review)IntentObjectHolder.getObject(reviewId.toString()) : null;
		return review;
	}
	
	public static Review get(Bundle addressedTo) {
		RDId reviewId = addressedTo.getParcelable(REVIEW_ID);
		Review review = reviewId != null? (Review)IntentObjectHolder.getObject(reviewId.toString()) : null;
		return review;
	}
}
