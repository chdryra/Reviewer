package com.chdryra.android.reviewer;

import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

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
		ReviewEditable r = FactoryReview.createReviewTreeEditable(title);
		mParent.addChild(r);
	}

	public void addChild(String title, float rating) {
		ReviewEditable r = FactoryReview.createReviewTreeEditable(title);
		r.setRating(rating);
		mParent.addChild(r);
	}
	
	public void addChildren(GVReviewSubjectRatingList children) {
		for(GVReviewSubjectRating child : children)
			addChild(child.getSubject(), child.getRating());
	}
	
	public void setChildren(GVReviewSubjectRatingList children) {
		removeAll();
		addChildren(children);
	}
	
	public void removeAll() {
		mParent.clearChildren();
	}
}
