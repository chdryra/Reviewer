/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;

import com.chdryra.android.reviewer.GvSubjectRatingList.GvSubjectRating;

/**
 * UI Fragment: collection of reviews. Currently used for editing sub-reviews (criteria). Each
 * grid cell shows a subject and rating.
 * <p/>
 * <p>
 * Also an ActionBar icon for setting the review score as an average of the sub-reviews.
 * </p>
 */
public class FragmentReviewChildren extends FragmentReviewGridAddEdit<GvSubjectRating> {
    private boolean mTotalRatingIsAverage;

    public FragmentReviewChildren() {
        super(GvDataList.GvType.CHILDREN, new InputHandlerChildren());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mTotalRatingIsAverage) setAverageRating();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTotalRatingIsAverage = getNodeController().isReviewRatingAverage();
        setIsEditable(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_review_children, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.menu_item_average_rating) {
            setTotalRatingIsAverage(true);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initRatingBarUI() {
        getRatingBar().setIsIndicator(false);
        getRatingBar().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                getEditableController().setRating(rating);
                if (fromUser) setTotalRatingIsAverage(false);
            }
        });
    }

    private void setTotalRatingIsAverage(boolean isAverage) {
        mTotalRatingIsAverage = isAverage;
        getNodeController().setReviewRatingAverage(mTotalRatingIsAverage);
        if (mTotalRatingIsAverage) setAverageRating();
    }

    private void setAverageRating() {
        GvSubjectRatingList children = (GvSubjectRatingList) getGridData();
        float rating = 0;
        for (GvSubjectRating child : children) {
            rating += child.getRating() / getGridData().size();
        }
        getRatingBar().setRating(rating);
    }
}
