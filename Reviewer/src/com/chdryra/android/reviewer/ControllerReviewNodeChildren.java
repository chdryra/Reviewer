package com.chdryra.android.reviewer;

public class ControllerReviewNodeChildren extends ControllerReviewNodeCollection{	
	private ReviewNode mParent;
	private CollectionReviewNode mChildrenBackup;
	private RCollection<String> mOriginalChildNames;
	private RCollection<Float> mOriginalChildRatings;
	
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
	
	public void clear() {
		mParent.clearChildren();
	}
	
	public void backupChildrenNamesAndRatings() {
		CollectionReviewNode children = mParent.getChildren();
		mChildrenBackup = new CollectionReviewNode();
		mChildrenBackup.add(children);
		
		mOriginalChildNames = new RCollection<String>();
		mOriginalChildRatings = new RCollection<Float>();
		for(ReviewNode child : children) {
			mOriginalChildNames.put(child.getID(), child.getTitle().get());
			mOriginalChildRatings.put(child.getID(), child.getRating().get());
		}
	}
	
	public void revertToBackUp() {
		if(mChildrenBackup != null) {
			clear();
			mParent.addChildren(mChildrenBackup);
		}
		
		for(ReviewNode child : mParent.getChildren()) {
			child.setTitle(mOriginalChildNames.get(child.getID()));
			child.setRating(mOriginalChildRatings.get(child.getID()));
		}
		
		mOriginalChildNames = null;
		mOriginalChildRatings = null;
	}
}
