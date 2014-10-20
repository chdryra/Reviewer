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

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVTagList.GVTag;

/**
 * Dialog for adding tags.
 */
public class DialogTagAddFragment extends DialogAddReviewDataFragment<GVTag> {
    private ClearableAutoCompleteTextView mTagEditText;

    public DialogTagAddFragment() {
        super(GVType.TAGS);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
        mTagEditText = (ClearableAutoCompleteTextView) v.findViewById(R.id.tag_edit_text);
        setKeyboardDoActionOnEditText(mTagEditText);

        return v;
    }

    @Override
    protected GVTag createGVData() {
        return new GVTag(mTagEditText.getText().toString().trim());
    }

    @Override
    protected void updateDialogOnAdd(GVTag tag) {
        mTagEditText.setText(null);
        getDialog().setTitle("+ #" + tag.get());
    }
}
