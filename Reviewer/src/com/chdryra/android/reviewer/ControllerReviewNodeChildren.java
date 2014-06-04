package com.chdryra.android.reviewer;

import com.chdryra.android.reviewer.GVCriterionList.GVCriterion;

public class ControllerReviewNodeChildren extends ControllerReviewNodeCollection{	
	private ReviewNode mParent;
	
	public ControllerReviewNodeChildren(ReviewNode parentNode) {
		super(parentNode.getChildren());
		mParent = parentNode;
	} 
	
	public void removeChild(String childId) {
		mParent.removeChild(Controller.convertID(childId));
	}
	
	public void addChild(String title) {
		Review r = FactoryReview.createUserReview(title);
		mParent.addChild(r);
	}

	public void addChild(String title, float rating) {
		Review r = FactoryReview.createUserReview(title);
		r.setRating(rating);
		mParent.addChild(r);
	}
	
	public void addChildren(GVCriterionList children) {
		for(GVCriterion child : children)
			addChild(child.getSubject(), child.getRating());
	}
	
	public void setChildren(GVCriterionList children) {
		removeAll();
		addChildren(children);
	}
	
	public void removeAll() {
		mParent.clearChildren();
	}
}
