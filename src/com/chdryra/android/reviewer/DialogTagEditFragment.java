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
import com.chdryra.android.reviewer.GVTagList.GVTag;

/**
 * Dialog for editing tags.
 */
public class DialogTagEditFragment extends DialogEditReviewDataFragment<GVTag> {
    private ClearableAutoCompleteTextView mTagEditText;

    public DialogTagEditFragment() {
        super(GVReviewDataList.GVType.TAGS);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
        mTagEditText = (ClearableAutoCompleteTextView) v.findViewById(R.id.tag_edit_text);
        mTagEditText.setText(getDatum().get());
        setKeyboardDoDoneOnEditText(mTagEditText);
        setDeleteWhatTitle(getDatum().get());

        return v;
    }

    @Override
    protected GVTagList.GVTag createGVData() {
        return new GVTagList.GVTag(mTagEditText.getText().toString().trim());
    }
}
