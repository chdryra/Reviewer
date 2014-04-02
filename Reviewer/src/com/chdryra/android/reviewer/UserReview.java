package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class UserReview implements ReviewNode{	
	private static final String TAG = "UserReview";
	
	private ReviewComment mComment;
	private ReviewImage mImage;
	private ReviewLocation mLocation;	
	private ReviewFacts mFacts;

	private ReviewNode mNode;

	public UserReview(String title) {
		mNode = (ReviewNode)ReviewFactory.createReviewNode(title);
	}

	public UserReview(Parcel in) {
		mNode = in.readParcelable(null);

		mComment = in.readParcelable(null);
		mImage = in.readParcelable(ReviewImage.class.getClassLoader());
		mLocation = in.readParcelable(ReviewLocation.class.getClassLoader());	
		mFacts = in.readParcelable(ReviewFacts.class.getClassLoader());
	}

	@Override
	public Review getReview() {
		return mNode.getReview();
	}
	
	@Override
	public ReviewID getID() {
		return mNode.getID();
	}

	@Override
	public String getTitle() {
		return mNode.getTitle();
	}

	@Override
	public void setTitle(String title) {
		mNode.setTitle(title);
	}

	@Override
	public void setRating(float rating) {
		mNode.setRating(rating);
	}

	@Override
	public float getRating() {
		return mNode.getRating();
	}

	public ReviewNodeCollection getCriteria() {
		return getChildren();
	}
	
	public void setCriteria(ReviewNodeCollection criteria) {
		addChildren(criteria);
	}
		
	public ReviewImage getImage() {
		return mImage;
	}
	
	public void setImage(ReviewImage image) {
		mImage = image;
	}
	
	public void deleteImage() {
		setImage(null);
	}
	
	public boolean hasImage() {
		return mImage != null;
	}
	
	public ReviewLocation getLocation() {
		return mLocation;
	}
	
	public void setLocation(ReviewLocation location) {
		mLocation = location;
	}
	
	public void deleteLocation() {
		setLocation(null);
	}
	
	public boolean hasLocation() {
		return mLocation != null;
	}

	public ReviewFacts getFacts() {
		return mFacts;
	}

	public void setFacts(ReviewFacts reviewFacts) {
		mFacts = reviewFacts;
	}
	
	public void deleteFacts() {
		setFacts(null);
	}
	
	public boolean hasFacts() {
		return mFacts != null && mFacts.size() > 0;
	}
	
	@Override
	public void acceptVisitor(VisitorReviewNode visitorReviewNode) {
		visitorReviewNode.visit(mNode);
	}

	@Override
	public void setComment(ReviewComment comment){
		mComment = comment;
	}

	@Override
	public ReviewComment getComment() {
		return mComment;
	}

	@Override
	public void deleteComment() {
		setComment(null);
	}

	public void deleteCommentIncludingCriteria() {
		deleteComment();
		VisitorCommentDeleter deleter = new VisitorCommentDeleter();
		for(ReviewNode c : getCriteria())
			c.acceptVisitor(deleter);
	}
	
	@Override
	public boolean hasComment() {
		return mComment != null;
	}

	@Override
	public void setParent(Review parent) {
		mNode.setParent(parent);
	}

	@Override
	public void setParent(ReviewNode parentNode) {
		mNode.setParent(parentNode);
	}

	@Override
	public void addChild(ReviewNode childNode) {
		mNode.addChild(childNode);
	}
	
	@Override
	public ReviewNode getParent() {
		return mNode.getParent();
	}

	@Override
	public void addChild(Review child) {
		mNode.addChild(child);
	}

	@Override
	public void removeChild(Review child) {
		mNode.removeChild(child);
	}
	
	@Override
	public void addChildren(ReviewNodeCollection children) {
		mNode.addChildren(children);
	}

	@Override
	public ReviewNodeCollection getChildren() {
		return mNode.getChildren();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(mNode, flags);

		dest.writeParcelable(mComment, flags);
		dest.writeParcelable(mImage, flags);
		dest.writeParcelable(mLocation, flags);	
		dest.writeParcelable(mFacts, flags);
	}
	
	public static final Parcelable.Creator<UserReview> CREATOR 
	= new Parcelable.Creator<UserReview>() {
	    public UserReview createFromParcel(Parcel in) {
	        return new UserReview(in);
	    }

	    public UserReview[] newArray(int size) {
	        return new UserReview[size];
	    }
	};
}
