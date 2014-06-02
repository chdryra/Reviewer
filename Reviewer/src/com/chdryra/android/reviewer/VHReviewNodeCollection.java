package com.chdryra.android.reviewer;

import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.reviewer.GVCriterionList.GVCriterion;

public class VHReviewNodeCollection extends ViewHolderBasic {
	private static final int LAYOUT = R.layout.grid_cell_review;
	private static final int SUBJECT = R.id.review_subject_text_view;
	private static final int RATING = R.id.total_rating_bar;
	
	private TextView mSubject;
    private RatingBar mRating;
    
    public VHReviewNodeCollection() {
    	super(LAYOUT);
    }
    
    @Override
    protected void initViewsToUpdate() {
    	mSubject = (TextView)getView(SUBJECT);
    	mRating = (RatingBar)getView(RATING);
    }
    
	@Override
	public void updateView(GVData data) {
		GVCriterion criterion = (GVCriterion) data;
		if(criterion != null) {
			mSubject.setText(criterion.getSubject());
			mRating.setRating(criterion.getRating());
		}
	}
}
