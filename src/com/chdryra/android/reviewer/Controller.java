/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;

public class Controller {
	public static final String CONTROLLER_ID = "com.chdryra.android.reviewer.review_id";	
	private ControllerReviewNode mReviewInProgress;
	private HashMap<String, ControllerReviewNode> mRNControllers;

	public Controller() {
		ReviewEditable r = FactoryReview.createReviewInProgress();
		mReviewInProgress = new ControllerReviewNode(r);
		mRNControllers = new HashMap<String, ControllerReviewNode>();
	}
	
	static RDId convertID(String id) {
		return RDId.generateID(id);
	}

	public ControllerReviewNode getReviewInProgress() {
		return mReviewInProgress;
	}
	
	public void pack(ControllerReviewNode controller, Intent i) {
			i.putExtra(CONTROLLER_ID, controller.getID());
			register(controller);
	}
	
	public Bundle pack(ControllerReviewNode controller) {
		Bundle args = new Bundle();
		args.putString(CONTROLLER_ID, controller.getID());
		register(controller);
		return args;
	}

	public ControllerReviewNode unpack(Bundle args) {
		ControllerReviewNode controller = args != null? getControllerFor(args.getString(CONTROLLER_ID)) : null; 		
		unregister(controller);
		
		return controller;
	}

	private ControllerReviewNode getControllerFor(String id) {
		return get(id);
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
}
