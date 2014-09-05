package com.chdryra.android.reviewer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.reviewer.GVReviewOverviewList.GVReviewOverview;

public class VHReviewNodeOverview extends ViewHolderBasic {
	private static final int LAYOUT = R.layout.grid_cell_review_overview;
	private static final int SUBJECT = R.id.review_subject;
	private static final int RATING = R.id.review_rating_bar;
	private static final int IMAGE = R.id.review_image;
	private static final int LOCATION = R.id.review_location;
	private static final int PUBLISH = R.id.review_publish_data;
	
	private TextView mSubject;
    private RatingBar mRating;
    private ImageView mImage;
    private TextView mLocation;
    private TextView mPublishData;
    
    public VHReviewNodeOverview() {
    	super(LAYOUT);
    }
    
    @Override
    protected void initViewsToUpdate() {
    	mSubject = (TextView)getView(SUBJECT);
    	mRating = (RatingBar)getView(RATING);
    	mImage = (ImageView)getView(IMAGE);
    	mLocation = (TextView)getView(LOCATION);
    	mPublishData = (TextView)getView(PUBLISH);
    }
    
	@Override
	public View updateView(GVData data) {
		GVReviewOverview review = (GVReviewOverview) data;
		if(review != null) {
			mSubject.setText(review.getSubject());
			mRating.setRating(review.getRating());
		}
		
		String location = review.getLocationName();
		String author = review.getAuthor();
		Date publishDate = review.getPublishDate();
		
		if(location != null && location.length() > 0)
			mLocation.setText("@" + location);
		
		DateFormat df = SimpleDateFormat.getDateInstance();
		DateFormat tf = SimpleDateFormat.getTimeInstance();
		String date = df.format(publishDate);
		String time = tf.format(publishDate);
		
		mPublishData.setText("on " + date + " at " + time + " by " + author);

		mImage.setImageBitmap(review.getCoverImage());
		
		return mInflated;
	}
}
