package com.chdryra.android.reviewer;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolder;
import com.chdryra.android.reviewer.GVCriterionList.GVCriterion;

public class VHReviewNodeCollection implements ViewHolder{
	public static final int LAYOUT = R.layout.grid_cell_review;
	private TextView mSubject;
    private RatingBar mRating;
    
    public VHReviewNodeCollection(View convertView) {
    	init(convertView);
    }
    
    public VHReviewNodeCollection(Context context) {
    	init(View.inflate(context, LAYOUT, null));
    }
    
    public void init(View view) {
    	mSubject = (TextView)view.findViewById(R.id.review_subject_text_view);
    	mRating = (RatingBar)view.findViewById(R.id.total_rating_bar);
    }
    
	@Override
	public void updateView(Object data) {
		GVCriterion criterion = (GVCriterion) data;
		mSubject.setText(criterion.getSubject());
		mRating.setRating(criterion.getRating());
	}
}
