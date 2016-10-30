/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.ViewHolders;

import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolder;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.Viewholder.ViewHolderData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.reviewer.R;

/**
 * {@link ViewHolder}: {@link com.chdryra.android.reviewer
 * .GvSubjectRatingList.GvSubjectRating}. Shows
 * subject
 * and rating for review children
 * (criteria).
 */
public class VhCriterion extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.grid_cell_criterion;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating;

    private final int mLayout;
    private final int mSubjectId;
    private final int mRatingId;

    private TextView mSubject;
    private RatingBar mRating;

    public VhCriterion() {
        this(LAYOUT, SUBJECT, RATING);
    }

    protected VhCriterion(int layoutId, int subjectId, int ratingId) {
        super(layoutId, new int[]{subjectId, ratingId});
        mLayout = layoutId;
        mSubjectId = subjectId;
        mRatingId = ratingId;
    }

    @Override
    public void updateView(ViewHolderData data) {
        if (mSubject == null) mSubject = (TextView) getView(mSubjectId);
        if (mRating == null) mRating = (RatingBar) getView(mRatingId);

        GvCriterion criterion = (GvCriterion) data;
        if (criterion != null) {
            mSubject.setText(criterion.getSubject());
            mRating.setRating(criterion.getRating());
        }
    }
}
