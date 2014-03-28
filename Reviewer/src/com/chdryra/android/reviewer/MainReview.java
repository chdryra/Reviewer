package com.chdryra.android.reviewer;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.ReviewIDGenerator.ReviewID;
import com.google.android.gms.maps.model.LatLng;

public class MainReview implements ReviewNode{	
	private static final String TAG = "MainReview";
	
	private ReviewID mID;
	private String mTitle;
	private float mRating;
	
	private ReviewNode mNode;
	private boolean mRatingIsAverage;

	private ReviewComment mComment;
	private ReviewImage mImage;
	private ReviewLocation mLocation;	
	private ReviewFacts mReviewFacts;

	private Date mDate;	
	private boolean mDateWithTime = false;

	public MainReview(String title) {
		mID = ReviewIDGenerator.generateID();
		mTitle = title;
		mNode = (ReviewNode)ReviewFactory.getInstance().createReviewNode(this);
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
		return mRatingIsAverage? mNode.getChildren().getRating() : mRating;
	}
	
	public boolean isRatingIsAverage() {
		return mRatingIsAverage;
	}

	public void setRatingIsAverage(boolean ratingIsAverage) {
		mRatingIsAverage = ratingIsAverage;
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

	public ReviewCollection getCriteria() {
		return getChildren();
	}
	
	public void setCriteria(ReviewCollection criteria) {
		addChildren(criteria);
	}

	public void deleteCommentIncludingChildren() {
		VisitorCommentDeleter v = new VisitorCommentDeleter();
		acceptVisitor(v);
		getChildren().acceptVisitor(v);
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
		return mReviewFacts;
	}

	public void setFacts(ReviewFacts reviewFacts) {
		mReviewFacts = reviewFacts;
	}
	
	public void deleteFacts() {
		setFacts(null);
	}
	
	public boolean hasFacts() {
		return mReviewFacts != null && mReviewFacts.size() > 0;
	}
	
	@Override
	public void acceptVisitor(ReviewVisitor reviewVisitor) {
		reviewVisitor.visit(this);
		getChildren().acceptVisitor(reviewVisitor);
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
	public ReviewNode getParent() {
		return mNode.getParent();
	}

	@Override
	public void addChild(Review child) {
		mNode.addChild(child);
	}

	@Override
	public void addChildren(ReviewCollection children) {
		mNode.addChildren(children);
	}

	@Override
	public ReviewCollection getChildren() {
		return mNode.getChildren();
	}
	
}
