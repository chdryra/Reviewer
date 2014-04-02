package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class UserReview implements Review{	
	private static final String TAG = "UserReview";

	private ReviewNode mNode;

	private ReviewComment mComment;
	private ReviewImage mImage;
	private ReviewLocation mLocation;	
	private ReviewFacts mFacts;

	public UserReview(String title) {
		mNode = (ReviewNode)ReviewFactory.createSimpleReviewNode(title);
	}

	public UserReview(Parcel in) {
		mNode = in.readParcelable(ReviewNode.class.getClassLoader());
		
		mComment = in.readParcelable(null);
		mImage = in.readParcelable(ReviewImage.class.getClassLoader());
		mLocation = in.readParcelable(ReviewLocation.class.getClassLoader());	
		mFacts = in.readParcelable(ReviewFacts.class.getClassLoader());
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
		return mNode.getChildren();
	}
	
	public void setCriteria(ReviewNodeCollection criteria) {
		mNode.addChildren(criteria);
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
	public int describeContents() {
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
