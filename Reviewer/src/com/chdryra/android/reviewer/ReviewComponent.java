package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewComponent implements ReviewNode {
	
	private Review mReview;
	private ReviewNode mParent;
	private ReviewNodeCollection mChildren;
	
	public ReviewComponent(Review node) {
		mReview = node;
	}
	
	@Override
	public Review getReview() {
		return mReview;
	}
	
	@Override
	public void setParent(Review parent) {
	    setParent(new ReviewComponent(parent));
	}
	
	@Override
	public void setParent(ReviewNode parentNode) {
		if(mParent != null && mParent.getID().equals(parentNode.getID()))
			return;
		mParent = parentNode;
		mParent.addChild(this);
	}
	
	@Override
	public ReviewNode getParent() {
		return mParent;
	}
	
	@Override
	public void addChild(Review child) {
		addChild(new ReviewComponent(child));
	}

	@Override
	public void addChild(ReviewNode childNode) {
	    if(mChildren.containsID(childNode.getID()))
	    	return;
		mChildren.put(childNode.getID(), childNode);
	    childNode.setParent(this);
	}
	
	@Override
	public void removeChild(Review child) {
		mChildren.remove(child.getID());
	}
	
	@Override
	public void addChildren(ReviewNodeCollection children) {
		for(Review child: children)
			addChild(child);
	}
	
	@Override
	public ReviewNodeCollection getChildren() {
		return mChildren;
	}
	
	@Override
	public ReviewID getID() {
		return mReview.getID();
	}

	@Override
	public String getTitle() {
		return mReview.getTitle();
	}

	@Override
	public void setTitle(String title) {
		mReview.setTitle(title);
	}

	@Override
	public float getRating() {
		return mReview.getRating();
	}

	@Override
	public void setRating(float rating) {
		mReview.setRating(rating);
	}

	@Override
	public void setComment(ReviewComment comment) {
		mReview.setComment(comment);
	}

	@Override
	public ReviewComment getComment() {
		return mReview.getComment();
	}

	@Override
	public void deleteComment() {
		mReview.deleteComment();
	}

	@Override
	public boolean hasComment() {
		return mReview.hasComment();
	}

	@Override
	public ReviewImage getImage() {
		return mReview.getImage();
	}

	@Override
	public void setImage(ReviewImage image) {
	}

	@Override
	public void deleteImage() {
	}

	@Override
	public boolean hasImage() {
		return mReview.hasImage();
	}

	@Override
	public ReviewLocation getLocation() {
		return mReview.getLocation();
	}

	@Override
	public void setLocation(ReviewLocation location) {
	}

	@Override
	public void deleteLocation() {
	}

	@Override
	public boolean hasLocation() {
		return mReview.hasLocation();
	}

	@Override
	public ReviewFacts getFacts() {
		return mReview.getFacts();
	}

	@Override
	public void setFacts(ReviewFacts facts) {
	}

	@Override
	public void deleteFacts() {
	}

	@Override
	public boolean hasFacts() {
		return mReview.hasFacts();
	}

	@Override
	public void acceptVisitor(ReviewNodeVisitor reviewNodeVisitor) {
		reviewNodeVisitor.visit(this);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mReview, flags);
		dest.writeParcelable(mParent, flags);
		dest.writeParcelable(mChildren, flags);
	}

	public ReviewComponent(Parcel in) {
		mReview = in.readParcelable(null);
		mParent = in.readParcelable(null);
		mChildren = in.readParcelable(null);
	}
	
	public static final Parcelable.Creator<ReviewComponent> CREATOR 
	= new Parcelable.Creator<ReviewComponent>() {
	    public ReviewComponent createFromParcel(Parcel in) {
	        return new ReviewComponent(in);
	    }

	    public ReviewComponent[] newArray(int size) {
	        return new ReviewComponent[size];
	    }
	};
}
