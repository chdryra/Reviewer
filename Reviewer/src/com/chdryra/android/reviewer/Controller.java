package com.chdryra.android.reviewer;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;

public class Controller {
	public static final String CONTROLLER_ID = "com.chdryra.android.reviewer.review_id";
	
	private static Controller sController;
	
	private CollectionReviewNode mReviews;
	private HashMap<String, ControllerReviewNode> mRNControllers;
	
	private Controller() {
		mReviews = new CollectionReviewNode();
		mRNControllers = new HashMap<String, ControllerReviewNode>();
	}
	
	public static Controller getInstance() {
		if(sController == null)
			sController = new Controller();
		
		return sController;
	}
	
	static RDId convertID(String id) {
		return RDId.generateID(id);
	}

	public static ControllerReviewNode getControllerFor(String id) {
		return getInstance().get(id);
	}
	
	public void add(ReviewNode review) {
		mReviews.add(review);
	}
	
	private ControllerReviewNode get(String id) {
		RDId rDId = RDId.generateID(id);
		ControllerReviewNode controller = null;
		if(!mRNControllers.containsKey(id)) {
			if(mReviews.containsID(rDId)) {
				controller = new ControllerReviewNode(mReviews.get(rDId));
				mRNControllers.put(id, controller);
			}
		}
		else
			controller = mRNControllers.get(id);
				
		return controller;
	}
	
	public static ControllerReviewNode addNewReviewInProgress() {
		Review r = FactoryReview.createUserReview("");
		ReviewNode node = r.getReviewNode();
		getInstance().add(node);
		
		return getControllerFor(node.getID().toString());
	}
	
	public static void pack(ControllerReviewNode controller, Intent i) {
			i.putExtra(CONTROLLER_ID, controller.getID());
	}
	
	public static Bundle pack(ControllerReviewNode controller) {
		Bundle args = new Bundle();
		args.putString(CONTROLLER_ID, controller.getID());
		return args;
	}

	public static ControllerReviewNode unpack(Bundle args) {
		ControllerReviewNode controller = null;
		if(args != null)
			controller = getControllerFor(args.getString(CONTROLLER_ID));
		
		return controller;
	}
	
}
