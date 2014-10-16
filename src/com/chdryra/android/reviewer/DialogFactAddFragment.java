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
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * Dialog for adding facts: asks for a label and value.
 */
public class DialogFactAddFragment extends DialogAddReviewDataFragment<GVFact> {
    private ClearableEditText mFactLabelEditText;
    private ClearableEditText mFactValueEditText;

    public DialogFactAddFragment() {
        super(GVType.FACTS);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_fact, null);
        mFactLabelEditText = (ClearableEditText) v.findViewById(R.id.fact_label_edit_text);
        mFactValueEditText = (ClearableEditText) v.findViewById(R.id.fact_value_edit_text);

        setKeyboardDoActionOnEditText(mFactValueEditText);

        return v;
    }

    @Override
    protected GVFact createGVData() {
        return new GVFact(mFactLabelEditText.getText().toString().trim(),
                mFactValueEditText.getText().toString().trim());
    }

    @Override
    protected void resetDialogOnAdd(GVFact newDatum) {
        mFactLabelEditText.setText(null);
        mFactValueEditText.setText(null);
        getDialog().setTitle("Added " + newDatum.getLabel() + ": " + newDatum.getValue());
        mFactLabelEditText.requestFocus();
    }
}
