package com.chdryra.android.reviewer;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import android.graphics.Bitmap;

import com.chdryra.android.reviewer.ReviewIDGenerator.ReviewID;
import com.google.android.gms.maps.model.LatLng;

public class MainReview implements Commentable{	
	private static final String COMMENT_HEADLINE_DELIMITER = ".!?";
	
	private ReviewID mID;
	private String mSubject;
	private float mRating;
	
	private CriterionList mCriteriaList = new CriterionList();
	private boolean mRatingIsAverage;
	
	private Date mDate;	
	private boolean mDateWithTime = false;
	
	private String mComment;

	private ReviewImage mImage;
	private ReviewLocation mLocation = ReviewLocation.getNullLocation();	
	private ReviewData mReviewData;
	
	public MainReview() {
		mID = ReviewIDGenerator.generateID();
		mDate = new Date();
	}

	public ReviewID getID() {
		return mID;
	}
	
	public void setSubject(String subject) {
		mSubject = subject;
	}
	
	public String getSubject() {
		return mSubject;
	}
	
	public void setRating(float rating) {
		mRating = rating;
	}
	
	public float getRating() {
		return mRating;
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

	public CriterionList getCriteriaList() {
		return mCriteriaList;
	}
	
	public void setCriteriaList(CriterionList criteriaList) {
		mCriteriaList = criteriaList;
	}

	private String getFormattedComment() {
		String comment = null;
		
		if(mComment != null)
			comment = mComment;
		
		LinkedHashMap<String, Criterion> criteria = getCriteriaList().getCriterionHashMap();
		Iterator<Criterion> it = criteria.values().iterator();
		boolean firstComment = true;
		while (it.hasNext()) {
			Criterion criterion = it.next();
			if(criterion.getComment() != null) {
				if(firstComment) {
					comment += String.format("%n");
					firstComment = false;
				}
			
				comment +=  String.format("%n") + "*" + criterion.getName() + ": " + criterion.getComment();
			}
		}
				
		return comment;
	}

	public String getCommentIncludingCriteria() {
		return getFormattedComment();
	}

	public void deleteCommentIncludingCriteria() {
		deleteComment();
		mCriteriaList.deleteComments();
	}
	
	@Override
	public String getCommentTitle() {
		return mSubject;
	}
	
	@Override
	public void setComment(String comment) {
		mComment = comment;
	}

	@Override
	public String getComment() {
		return mComment;
	}
	
	public String getCommentHeadline() {
		if(mComment != null) {
			StringTokenizer tokens = new StringTokenizer(mComment, COMMENT_HEADLINE_DELIMITER);
			return tokens.nextToken();
		} else
			return null;
	}
	
	@Override
	public void deleteComment() {
		mComment = null;
	}

	@Override
	public boolean hasComment() {
		return mComment != null || mCriteriaList.hasComment();
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
		setLocation(ReviewLocation.getNullLocation());
	}
	
	public boolean hasLocation() {
		return mLocation.getLatLng() != null;
	}

	public ReviewData getData() {
		return mReviewData;
	}

	public void setData(ReviewData reviewData) {
		mReviewData = reviewData;
	}
	
	public void deleteData() {
		setData(null);
	}
	
	public boolean hasData() {
		return mReviewData!=null;
	}
	
}
