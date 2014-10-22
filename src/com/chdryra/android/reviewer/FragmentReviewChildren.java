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

import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

/**
 * UI Fragment: collection of reviews. Currently used for editing sub-reviews (criteria). Each
 * grid cell shows a subject and rating.
 * <p/>
 * <p>
 * FragmentReviewGrid functionality:
 * <ul>
 * <li>Subject: enabled</li>
 * <li>RatingBar: enabled</li>
 * <li>Banner button: launches AddChild dialog</li>
 * <li>Grid cell click: launches EditChild dialog</li>
 * <li>Grid cell long click: same as click</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * Also an ActionBar icon for setting the review score as an average of the sub-reviews.
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewChildren
 * @see com.chdryra.android.reviewer.ConfigAddEditActivity.AddChild
 * @see com.chdryra.android.reviewer.ConfigAddEditActivity.EditChild
 */
public class FragmentReviewChildren extends FragmentReviewGridAddEdit<GVReviewSubjectRating> {
    private boolean mTotalRatingIsAverage;

    public FragmentReviewChildren() {
        super(GVType.CHILDREN);
        mHandler = new InputHandlerChildren();
    }

    @Override
    public boolean onDialogAddClick(Intent data) {
        GVReviewSubjectRating item = getInputHandler().unpack(InputHandlerReviewData
                .CurrentNewDatum.NEW, data);
        if (((GVReviewSubjectRatingList) getGridData()).contains(item.getSubject())) {
            getInputHandler().makeToastHasItem(getActivity());
            return false;
        } else {
            return super.onDialogAddClick(data);
        }
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
    protected void initRatingBarUI() {
        getTotalRatingBar().setIsIndicator(false);
        getTotalRatingBar().setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                getEditableController().setRating(rating);
                if (fromUser) setTotalRatingIsAverage(false);
            }
        });
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

    private void setTotalRatingIsAverage(boolean isAverage) {
        mTotalRatingIsAverage = isAverage;
        getNodeController().setReviewRatingAverage(mTotalRatingIsAverage);
        if (mTotalRatingIsAverage) setAverageRating();
    }

    private void setAverageRating() {
        float rating = 0;
        for (GVReviewSubjectRating child : getGridData()) {
            rating += child.getRating() / getGridData().size();
        }
        getTotalRatingBar().setRating(rating);
    }
}
