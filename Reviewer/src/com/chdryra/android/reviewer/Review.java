package com.chdryra.android.reviewer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import android.util.Log;
import android.widget.RatingBar;

public class Review {	
	private static final String TAG = "Review";
	
	private Date mDate;	
	private String mSubject;
	private ArrayList<Criterion> mCriteriaList;	
	private String mReviewText;
	private float mRating;
	
	public Review() {
		
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

	public void setDate(Date date) {
		mDate = date;
	}

	public Date getDate() {
		return mDate;
	}
	
	public String getReviewText() {
		return mReviewText;
	}

	public ArrayList<Criterion> getCriteriaList() {
		return mCriteriaList;
	}

	public void setCriteriaList(ArrayList<Criterion> criteriaList) {
		mCriteriaList = criteriaList;
	}
	
	public void addCriterion(Criterion criterion) {
		mCriteriaList.add(criterion);
	}
	
	}
