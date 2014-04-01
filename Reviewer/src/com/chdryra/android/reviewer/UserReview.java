package com.chdryra.android.reviewer;

import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;


public class UserReview implements ReviewNode{	
	private static final String TAG = "UserReview";
	
	private ReviewID mID;
	private String mTitle;
	private float mRating;
	
	private ReviewNode mNode;

	private ReviewComment mComment;
	private ReviewImage mImage;
	private ReviewLocation mLocation;	
	private ReviewFacts mFacts;

	private Date mDate;	
	private boolean mDateWithTime = false;

	public UserReview(String title) {
		mID = ReviewID.generateID();
		mTitle = title;
		mNode = (ReviewNode)ReviewFactory.createReviewNode(this);
	}

	public UserReview(Parcel in) {
		mID = in.readParcelable(ReviewID.class.getClassLoader());
		mTitle = in.readString();
		mRating = in.readFloat();
		
		mNode = in.readParcelable(null);

		mComment = in.readParcelable(null);
		mImage = in.readParcelable(ReviewImage.class.getClassLoader());
		mLocation = in.readParcelable(ReviewLocation.class.getClassLoader());	
		mFacts = in.readParcelable(ReviewFacts.class.getClassLoader());

		mDate = new Date(in.readLong());	
		mDateWithTime = in.readByte() != 0;
	}

	@Override
	public Review getReview() {
		return mNode.getReview();
	}
	
	@Override
	public ReviewID getID() {
		return mID;
	}

	@Override
	public String getTitle() {
		return mTitle;
	}

	@Override
	public void setTitle(String title) {
		mTitle = title;
	}

	@Override
	public void setRating(float rating) {
		mRating = rating;
	}

	@Override
	public float getRating() {
		return mRating;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public Date getDate() {
		return mDate;
	}
	
	public boolean isDateWithTime() {
		return mDateWithTime;
	}

	public void setDateWithTime(boolean dateWithTime) {
		mDateWithTime = dateWithTime;
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
	public void acceptVisitor(ReviewNodeVisitor reviewNodeVisitor) {
		reviewNodeVisitor.visit(this);
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
		dest.writeParcelable(mID, flags);
		dest.writeString(mTitle);
		dest.writeFloat(mRating);
		
		dest.writeParcelable(mNode, flags);

		dest.writeParcelable(mComment, flags);
		dest.writeParcelable(mImage, flags);
		dest.writeParcelable(mLocation, flags);	
		dest.writeParcelable(mFacts, flags);

		dest.writeLong(mDate.getTime());	
		dest.writeByte((byte) (mDateWithTime? 1 : 0));
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
