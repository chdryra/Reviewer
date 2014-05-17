package com.chdryra.android.reviewer;

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
	
	public void removeAll() {
		mParent.clearChildren();
	}
}
