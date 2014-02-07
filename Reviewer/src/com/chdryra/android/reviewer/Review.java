package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RatingBar;

public class Review implements Commentable{	
	private static final String TAG = "Review";
	private static final String GENERAL_COMMENT_TITLE = "General";
	
	private UUID mID;
	private String mSubject;
	private Date mDate;	
	private CriterionList mCriteriaList;
	private float mRating;
	private boolean mRatingIsAverage;
	private String mComment;
	private Bitmap mImage;
	private Location mLocation;
	
	public Review() {
		generateID();
		mDate = new Date();
	}
	
	private void generateID() {
		mID = UUID.randomUUID();
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
	
	public CriterionList getCriteriaList() {
		return mCriteriaList;
	}

	public void setCriteriaList(CriterionList criteriaList) {
		mCriteriaList = criteriaList;
	}

	private String getFormattedComment() {
		String comment = new String();
		if(mComment != null)
			comment += mComment + String.format("%n");
		
		LinkedHashMap<String, Criterion> criteria = getCriteriaList().getCriterionHashMap();
		Iterator<Criterion> it = criteria.values().iterator();
		while (it.hasNext()) {
			Criterion criterion = it.next();
			if(criterion.getComment() != null)
				comment +=  String.format("%n") + "*" + criterion.getName() + ": " + criterion.getComment();			
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
		return GENERAL_COMMENT_TITLE;
	}
	
	@Override
	public void setComment(String comment) {
		mComment = comment;
	}

	@Override
	public String getComment() {
		return mComment;
	}
	
	@Override
	public void deleteComment() {
		mComment = null;
	}

	public Bitmap getImage() {
		return mImage;
	}

	public void setImage(Bitmap image) {
		mImage = image;
	}

	
}
