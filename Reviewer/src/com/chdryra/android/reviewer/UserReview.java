package com.chdryra.android.reviewer;

import android.os.Parcel;
import android.os.Parcelable;

public class UserReview implements Review{	
	private ReviewNode mNode;

	private RDComment mComment;
	private RDImage mImage;
	private RDLocation mLocation;	
	private RDFacts mFacts;
	private RDUrl mURL;
	private RDDate mDate;

	public UserReview(String title) {
		mNode = (ReviewNode)ReviewFactory.createSimpleReviewNode(title);
		
		//Null option data
		mComment = new RDCommentSingle();
		mImage = new RDImage();
		mLocation = new RDLocation();
		mFacts = new RDFacts();
		mURL = new RDUrl();
		mDate = new RDDate();
	}

	public UserReview(Parcel in) {
		mNode = in.readParcelable(ReviewNode.class.getClassLoader());
		
		setComment((RDComment)in.readParcelable(RDComment.class.getClassLoader()));
		setImage((RDImage)in.readParcelable(RDImage.class.getClassLoader()));
		setLocation((RDLocation)in.readParcelable(RDLocation.class.getClassLoader()));	
		setFacts((RDFacts)in.readParcelable(RDFacts.class.getClassLoader()));
		setURL((RDUrl)in.readParcelable(RDUrl.class.getClassLoader()));
		setDate((RDDate)in.readParcelable(RDDate.class.getClassLoader()));
	}
	
	@Override
	public ReviewID getID() {
		return mNode.getID();
	}

	@Override
	public RDTitle getTitle() {
		return mNode.getTitle();
	}

	@Override
	public void setTitle(String title) {
		mNode.setTitle(title);
		mNode.getTitle().setHoldingReview(this);
	}

	@Override
	public void setRating(float rating) {
		mNode.setRating(rating);
		mNode.getRating().setHoldingReview(this);
	}

	@Override
	public RDRating getRating() {
		return mNode.getRating();
	}

	public ReviewCollection getCriteria() {
		return mNode.getChildrenReviews();
	}
	
	public ReviewNodeCollection getCriteriaNodes() {
		return mNode.getChildren();
	}
	
	public void setCriteria(ReviewCollection criteria) {
		mNode.addChildren(criteria);
	}
		
	private ReviewData processData(ReviewData newData, ReviewData ifNull) {
		ReviewData member;
		if(newData != null)
			member = newData;
		else
			member = ifNull;
		
		member.setHoldingReview(this);
		
		return member;
	}
	
	public RDImage getImage() {
		return mImage;
	}
	
	public void setImage(RDImage image) {
		mImage = (RDImage)processData(image, new RDImage());
	}
	
	public void deleteImage() {
		setImage(null);
	}
	
	public boolean hasImage() {
		return mImage.hasData();
	}
	
	public RDLocation getLocation() {
		return mLocation;
	}
	
	public void setLocation(RDLocation location) {
		mLocation = (RDLocation) processData(location, new RDLocation());
	}
	
	public void deleteLocation() {
		setLocation(null);
	}
	
	public boolean hasLocation() {
		return mLocation.hasData();
	}

	public RDFacts getFacts() {
		return mFacts;
	}

	public void setFacts(RDFacts facts) {
		mFacts = (RDFacts) processData(facts, new RDFacts());
	}
	
	public void deleteFacts() {
		setFacts(null);
	}
	
	public boolean hasFacts() {
		return mFacts.hasData();
	}
	
	@Override
	public void setComment(RDComment comment){
		mComment = (RDComment) processData(comment, new RDCommentSingle());
	}

	@Override
	public RDComment getComment() {
		return mComment;
	}

	@Override
	public void deleteComment() {
		setComment(null);
	}

	public void deleteCommentIncludingCriteria() {
		deleteComment();
		VisitorCommentDeleter deleter = new VisitorCommentDeleter();
		for(ReviewNode c : mNode.getChildren())
			c.acceptVisitor(deleter);
	}
	
	@Override
	public boolean hasComment() {
		return mComment.hasData();
	}


	@Override
	public RDUrl getURL() {
		return mURL;
	}

	@Override
	public void setURL(RDUrl url) {
		mURL = (RDUrl) processData(url, new RDUrl());
	}

	@Override
	public void deleteURL() {
		setURL(null);
	}

	@Override
	public boolean hasURL() {
		return mURL.hasData();
	}

	@Override
	public RDDate getDate() {
		return mDate;
	}
	
	@Override
	public void setDate(RDDate date) {
		mDate = (RDDate) processData(date, new RDDate());
	}
	
	@Override
	public void deleteDate() {
		setDate(null);
	}
	
	@Override
	public boolean hasDate() {
		return mDate.hasData();
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
		dest.writeParcelable(mURL, flags);
		dest.writeParcelable(mDate, flags);
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
