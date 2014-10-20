/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

/**
 * Dialog for editing sub-reviews: edit/delete subject and rating.
 */
public class DialogChildEditFragment extends DialogEditReviewDataFragment<GVReviewSubjectRating> {
    private DialogHolder<GVReviewSubjectRating> mDialogHolder;

    public DialogChildEditFragment() {
        super(GVReviewDataList.GVType.CHILDREN);
        mDialogHolder = new DHChild(this);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        mDialogHolder.inflate(getActivity());
        mDialogHolder.initialiseView(getDatum());

        return mDialogHolder.getView();
    }

    protected GVReviewSubjectRating createGVData() {
        return mDialogHolder.getGVData();
    }
}
