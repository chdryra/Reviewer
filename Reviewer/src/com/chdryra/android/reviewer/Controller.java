package com.chdryra.android.reviewer;

import java.util.HashMap;

public class Controller {
	private static Controller sController;
	
	private HashMap<String, ControllerReviewNode> mControllers;
	
	private Controller() {
		mControllers = new HashMap<String, ControllerReviewNode>();
	}
	
	public static Controller getInstance() {
		if(sController == null)
			sController = new Controller();
		
		return sController;
	}

	public static ControllerReviewNode getControllerFor(String id) {
		return getInstance().get(id);
	}
	
	public void add(ControllerReviewNode controller) {
		mControllers.put(controller.getID(), controller);
	}
	
	public ControllerReviewNode get(String id) {
		RDId rDId = RDId.generateID(id);
		return mControllers.get(rDId);
	}
	
	public static ControllerReviewNode createNewUserReview(String title) {
		Review r = FactoryReview.createUserReview(title);
		ControllerReviewNode controller = new ControllerReviewNode(r.getReviewNode());
		getInstance().add(controller);
		
		return controller;
	}
}
