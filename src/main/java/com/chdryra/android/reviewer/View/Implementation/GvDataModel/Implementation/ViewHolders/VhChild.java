/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.ViewHolders;

import android.widget.RatingBar;
import android.widget.TextView;

import com.chdryra.android.mygenerallibrary.ViewHolderBasic;
import com.chdryra.android.mygenerallibrary.ViewHolderData;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;

/**
 * {@link com.chdryra.android.mygenerallibrary.ViewHolder}: {@link com.chdryra.android.reviewer
 * .GvSubjectRatingList.GvSubjectRating}. Shows
 * subject
 * and rating for review children
 * (criteria).
 */
public class VhChild extends ViewHolderBasic {
    private static final int LAYOUT = R.layout.grid_cell_review_subject_rating;
    private static final int SUBJECT = R.id.review_subject;
    private static final int RATING = R.id.review_rating;

    private TextView mSubject;
    private RatingBar mRating;

    //Constructors
    public VhChild() {
        super(LAYOUT, new int[]{SUBJECT, RATING});
    }

    //Overridden
    @Override
    public void updateView(ViewHolderData data) {
        if (mSubject == null) mSubject = (TextView) getView(SUBJECT);
        if (mRating == null) mRating = (RatingBar) getView(RATING);

        GvCriterion criterion = (GvCriterion) data;
        if (criterion != null) {
            mSubject.setText(criterion.getSubject());
            mRating.setRating(criterion.getRating());
        }
    }
}
