package com.chdryra.android.reviewer;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.UUID;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class Review implements Commentable{	
	private static final String TAG = "Review";
	private static final String GENERAL_COMMENT_TITLE = "Overall";
	private static final String LOCATION_DELIMITER = ",";
	private static final String COMMENT_HEADLINE_DELIMITER = ".!?";
	
	private UUID mID;
	private String mSubject;
	private Date mDate;	
	private CriterionList mCriteriaList;
	private float mRating;
	private boolean mRatingIsAverage;
	private String mComment;
	private Bitmap mImage;
	private String mImageCaption;
	private LatLng mLatLng;
	private Bitmap mMapSnapshot;
	private String mLocationName;
	private NumberData mNumberData;
	
	public Review() {
		generateID();
		mDate = new Date();
	}
	
	private void generateID() {
		mID = UUID.randomUUID();
	}

	public UUID getID() {
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
		if(criteria.size() > 0 && comment != null)
			 comment += String.format("%n");
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
	
	public String getCommentHeadline() {
		StringTokenizer tokens = new StringTokenizer(mComment, COMMENT_HEADLINE_DELIMITER);
		return tokens.nextToken();
	}
	
	@Override
	public void deleteComment() {
		mComment = null;
	}

	public boolean hasComment() {
		return mComment != null;
	}
	
	public Bitmap getImage() {
		return mImage;
	}

	public void setImage(Bitmap image) {
		mImage = image;
	}

	public boolean hasImage() {
		return mImage != null;
	}
	
	public String getImageCaption() {
		return mImageCaption;
	}

	public void setImageCaption(String imageCaption) {
		mImageCaption = imageCaption;
	}

	public boolean hasImageCaption() {
		return mImageCaption != null;
	}
	
	public LatLng getLatLng() {
		return mLatLng;
	}

	public void setLatLng(LatLng latlng) {
		mLatLng = latlng;
	}

	public void deleteLatLng() {
		mLatLng = null;
		mMapSnapshot = null;
	}
	
	public boolean hasLatLng() {
		return mLatLng != null;
	}

	public void setMapSnapshot(Bitmap bitmap) {
		mMapSnapshot = bitmap;	
	}
	
	public Bitmap getMapSnapshot() {
		return mMapSnapshot;
	}

	public boolean hasMapSnapshot() {
		return mMapSnapshot != null;
	}

	public String getLocationName() {
		return mLocationName;
	}

	public void setLocationName(String locationName) {
		mLocationName = locationName;
	}
	
	public boolean hasLocationName() {
		return mLocationName != null;
	}
	
	public String getShortenedLocationName() {
		if(mLocationName == null)
			return null;
		
		String[] split = mLocationName.split(LOCATION_DELIMITER);
		return split[0];
	}

	public NumberData getNumberData() {
		return mNumberData;
	}

	public void setNumberData(NumberData numberData) {
		mNumberData = numberData;
	}
	
}
