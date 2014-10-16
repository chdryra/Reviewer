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

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.reviewer.GVFactList.GVFact;

/**
 * Dialog for editing facts: edit/delete label and value.
 */
public class DialogFactEditFragment extends DialogEditReviewDataFragment<GVFact> {
    private ClearableEditText mLabel;
    private ClearableEditText mValue;

    public DialogFactEditFragment() {
        super(GVReviewDataList.GVType.FACTS);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_fact, null);
        mLabel = (ClearableEditText) v.findViewById(R.id.fact_label_edit_text);
        mValue = (ClearableEditText) v.findViewById(R.id.fact_value_edit_text);

        mLabel.setText(getDatum().getLabel());
        mValue.setText(getDatum().getValue());

        setKeyboardDoDoneOnEditText(mValue);
        setDeleteWhatTitle(getDatum().getLabel() + ": " + getDatum().getValue());

        return v;
    }

    @Override
    protected GVFact createGVData() {
        return new GVFact(mLabel.getText().toString().trim(), mValue.getText().toString().trim());
    }
}
