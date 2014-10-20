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
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * Dialog for adding facts: asks for a label and value.
 */
public class DialogFactAddFragment extends DialogAddReviewDataFragment<GVFact> {
    private DialogHolder<GVFact> mDialogHolder;

    public DialogFactAddFragment() {
        super(GVType.FACTS);
        mDialogHolder = new DHFact(this);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        mDialogHolder.inflate(getActivity());
        mDialogHolder.initialiseView(null);

        return mDialogHolder.getView();
    }

    @Override
    protected GVFact createGVData() {
        return mDialogHolder.getGVData();
    }

    @Override
    protected void updateDialogOnAdd(GVFact newDatum) {
        mDialogHolder.updateView(newDatum);
    }
}
