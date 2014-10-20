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

import com.chdryra.android.reviewer.GVFactList.GVFact;

/**
 * Dialog for editing facts: edit/delete label and value.
 */
public class DialogFactEditFragment extends DialogEditReviewDataFragment<GVFact> {
    private DialogHolder<GVFact> mDialogHolder;

    public DialogFactEditFragment() {
        super(GVReviewDataList.GVType.FACTS);
        mDialogHolder = new DHFact(this);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        mDialogHolder.inflate(getActivity());
        mDialogHolder.initialiseView(getDatum());

        return mDialogHolder.getView();
    }

    @Override
    protected GVFact createGVData() {
        return mDialogHolder.getGVData();
    }
}
