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
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableEditText;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class DialogFactAddFragment extends DialogAddReviewDataFragment {
    public static final String FACT_LABEL = "com.chdryra.android.reviewer.fact_label";
    public static final String FACT_VALUE = "com.chdryra.android.reviewer.fact_value";

    private GVFactList        mFacts;
    private ClearableEditText mFactLabelEditText;
    private ClearableEditText mFactValueEditText;

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_fact, null);
        mFactLabelEditText = (ClearableEditText) v.findViewById(R.id.fact_label_edit_text);
        mFactValueEditText = (ClearableEditText) v.findViewById(R.id.fact_value_edit_text);
        setKeyboardIMEDoAction(mFactValueEditText);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFacts = (GVFactList) setAndInitData(GVType.FACTS);
        setDialogTitle(getResources().getString(R.string.dialog_add_fact_title));
    }

    @Override
    protected void OnAddButtonClick() {
        String label = mFactLabelEditText.getText().toString();
        String value = mFactValueEditText.getText().toString();
        if ((label == null || label.length() == 0) && (value == null || value.length() == 0)) {
            return;
        }

        if (label == null || label.length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.toast_enter_label),
                    Toast.LENGTH_SHORT).show();
        } else if (value == null || value.length() == 0) {
            Toast.makeText(getActivity(), getResources().getString(R.string.toast_enter_value),
                    Toast.LENGTH_SHORT).show();
        } else if (mFacts.containsLabel(label)) {
            Toast.makeText(getActivity(), getResources().getString(R.string.toast_has_fact),
                    Toast.LENGTH_SHORT).show();
        } else {
            mFacts.add(label, value);
            Intent i = getNewReturnData();
            i.putExtra(FACT_LABEL, label);
            i.putExtra(FACT_VALUE, value);
            mFactLabelEditText.setText(null);
            mFactValueEditText.setText(null);
            getDialog().setTitle("Added " + label + ": " + value);
            mFactLabelEditText.requestFocus();
        }
    }
}
