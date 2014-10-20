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

import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVReviewSubjectRatingList.GVReviewSubjectRating;

/**
 * Dialog for adding sub-reviews: asks for a subject and rating.
 */
public class DialogChildAddFragment extends DialogAddReviewDataFragment<GVReviewSubjectRating> {
    private DialogHolder<GVReviewSubjectRating> mDialogHolder;

    public DialogChildAddFragment() {
        super(GVType.CHILDREN);
        mDialogHolder = new DHChild(this);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        mDialogHolder.inflate(getActivity());
        mDialogHolder.initialiseView(null);

        return mDialogHolder.getView();
    }

    @Override
    protected GVReviewSubjectRating createGVData() {
        return mDialogHolder.getGVData();
    }

    @Override
    protected void updateDialogOnAdd(GVReviewSubjectRating newDatum) {
        mDialogHolder.updateView(newDatum);
    }
}
