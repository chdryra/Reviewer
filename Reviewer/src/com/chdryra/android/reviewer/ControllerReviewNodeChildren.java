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
		ReviewEditable r = FactoryReview.createChildReview(title, mParent);
		mParent.addChild(r);
	}

	public void addChild(String title, float rating) {
		ReviewEditable r = FactoryReview.createChildReview(title, mParent);
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
