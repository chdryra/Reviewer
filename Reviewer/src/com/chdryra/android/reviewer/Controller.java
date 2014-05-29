package com.chdryra.android.reviewer;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;

public class Controller {
	public static final String CONTROLLER_ID = "com.chdryra.android.reviewer.review_id";
	
	private static Controller sController;	
	private HashMap<String, ControllerReviewNode> mRNControllers;
	
	public enum GVType {
		COMMENTS,
		CRITERIA,
		IMAGES,	
		FACTS,
		PROS,
		CONS,
		PROCONS,
		URLS,
		LOCATIONS,
		TAGS
	}

	private Controller() {
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
		
	private void register(ControllerReviewNode controller) {
		if(!isRegistered(controller.getID()))
			mRNControllers.put(controller.getID(), controller);
	}
	
	private void unregister(ControllerReviewNode controller) {
		if(controller != null && isRegistered(controller.getID()))
			mRNControllers.remove(controller.getID());
	}
	
	private boolean isRegistered(String id) {
		return mRNControllers.containsKey(id);
	}
	
	private ControllerReviewNode get(String id) {
		return isRegistered(id)? mRNControllers.get(id) : null;
	}
	
	public static ControllerReviewNode addNewReviewInProgress() {
		Review r = FactoryReview.createUserReview("");
		
		return new ControllerReviewNode(r.getReviewNode());
	}
	
	public static void pack(ControllerReviewNode controller, Intent i) {
			i.putExtra(CONTROLLER_ID, controller.getID());
			getInstance().register(controller);
	}
	
	public static Bundle pack(ControllerReviewNode controller) {
		Bundle args = new Bundle();
		args.putString(CONTROLLER_ID, controller.getID());
		getInstance().register(controller);
		return args;
	}

	public static ControllerReviewNode unpack(Bundle args) {
		ControllerReviewNode controller = args != null? getControllerFor(args.getString(CONTROLLER_ID)) : null; 		
		getInstance().unregister(controller);
		
		return controller;
	}
	
}
