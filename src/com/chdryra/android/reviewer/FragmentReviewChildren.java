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
import android.widget.Toast;

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
 * <li>Banner button: launches DialogChildAddFragment</li>
 * <li>Grid cell click: launches DialogChildEditFragment</li>
 * <li>Grid cell long click: same as click</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * Also an ActionBar icon for setting the review score as an average of the sub-reviews.
 * </p>
 *
 * @see com.chdryra.android.reviewer.ActivityReviewChildren
 * @see com.chdryra.android.reviewer.DialogChildAddFragment
 * @see com.chdryra.android.reviewer.DialogChildEditFragment
 */
public class FragmentReviewChildren extends FragmentReviewGridAddEdit<GVReviewSubjectRating> {
    public static final String CHILD_SUBJECT = "com.chdryra.android.reviewer.child_subject";
    public static final String CHILD_RATING  = "com.chdryra.android.reviewer.child_rating";

    private GVReviewSubjectRatingList mReviewData;
    private boolean                   mTotalRatingIsAverage;

    public FragmentReviewChildren() {
        super(GVType.CHILDREN);
    }

    @Override
    protected void doAdd(Intent data) {
        String subject = (String) data.getSerializableExtra(DialogChildAddFragment.SUBJECT);
        if (subject != null && subject.length() > 0 && !mReviewData.contains(subject)) {
            mReviewData.add(subject, data.getFloatExtra(DialogChildAddFragment.RATING, 0));
        }
    }

    @Override
    protected void doDelete(Intent data) {
        mReviewData.remove((String) data.getSerializableExtra(DialogChildEditFragment
                .SUBJECT_OLD));
        if (mReviewData.size() == 0) setTotalRatingIsAverage(false);
    }

    @Override
    protected void doDone(Intent data) {
        String oldSubject = (String) data.getSerializableExtra(DialogChildEditFragment
                .SUBJECT_OLD);
        String newSubject = (String) data.getSerializableExtra(DialogChildEditFragment
                .SUBJECT);
        float newRating = data.getFloatExtra(DialogChildEditFragment.RATING, 0);
        if (oldSubject.equals(newSubject)) {
            mReviewData.set(oldSubject, newRating);
        } else {
            if (!mReviewData.contains(newSubject)) {
                mReviewData.remove(oldSubject);
                mReviewData.add(newSubject, newRating);
            } else {
                Toast.makeText(getActivity(), R.string.toast_exists_criterion,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected Bundle packGridCellData(GVReviewSubjectRating reviewData, Bundle args) {
        args.putString(CHILD_SUBJECT, reviewData.getSubject());
        args.putFloat(CHILD_RATING, reviewData.getRating());

        return args;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mTotalRatingIsAverage) {
            setAverageRating();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviewData = (GVReviewSubjectRatingList) getGridData();
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
        if (mTotalRatingIsAverage) {
            setAverageRating();
        }
    }

    private void setAverageRating() {
        float rating = 0;
        for (GVReviewSubjectRating child : mReviewData) {
            rating += child.getRating() / mReviewData.size();
        }
        getTotalRatingBar().setRating(rating);
    }
}
