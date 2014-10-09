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
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;

/**
 * Dialog for editing facts: edit/delete label and value.
 */
public class DialogFactEditFragment extends DialogCancelDeleteDoneFragment {
    public static final String FACT_OLD_LABEL = "com.chdryra.android.reviewer.datum_old_label";
    public static final String FACT_OLD_VALUE = "com.chdryra.android.reviewer.datum_old_value";

    private ClearableEditText mLabel;
    private ClearableEditText mValue;
    private String            mOldLabel;
    private String            mOldValue;

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_fact, null);
        mLabel = (ClearableEditText) v.findViewById(R.id.fact_label_edit_text);
        mValue = (ClearableEditText) v.findViewById(R.id.fact_value_edit_text);
        mLabel.setText(mOldLabel);
        mValue.setText(mOldValue);
        setKeyboardDoDoneOnEditText(mValue);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOldLabel = getArguments().getString(FragmentReviewFacts.FACT_LABEL);
        mOldValue = getArguments().getString(FragmentReviewFacts.FACT_VALUE);
        setDialogTitle(getResources().getString(R.string.dialog_edit_fact_title));
        setDeleteWhatTitle(mOldLabel + ": " + mOldValue);
    }

    @Override
    protected void onDeleteButtonClick() {
        Intent i = getNewReturnDataIntent();
        i.putExtra(FACT_OLD_LABEL, mOldLabel);
        i.putExtra(FACT_OLD_VALUE, mOldValue);
    }

    @Override
    protected boolean hasDataToDelete() {
        return true;
    }

    @Override
    protected void onDoneButtonClick() {
        Intent i = getNewReturnDataIntent();
        i.putExtra(FACT_OLD_LABEL, mOldLabel);
        i.putExtra(FACT_OLD_VALUE, mOldValue);
        i.putExtra(FragmentReviewFacts.FACT_LABEL, mLabel.getText().toString());
        i.putExtra(FragmentReviewFacts.FACT_VALUE, mValue.getText().toString());
    }
}
