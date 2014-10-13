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
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * Dialog for adding tags.
 */
public class DialogTagAddFragment extends DialogAddReviewDataFragment {
    public static final String TAG = "com.chdryra.android.review.TAG";

    private ClearableAutoCompleteTextView mTagEditText;

    @Override
    public GVType getGVType() {
        return GVType.TAGS;
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
        mTagEditText = (ClearableAutoCompleteTextView) v.findViewById(R.id.tag_edit_text);
        setKeyboardDoActionOnEditText(mTagEditText);

        return v;
    }

    @Override
    protected void onAddButtonClick() {
        String tag = mTagEditText.getText().toString();
        if (tag == null || tag.length() == 0) {
            return;
        }

        GVTagList tags = (GVTagList) getData();
        if (tags.contains(tag)) {
            Toast.makeText(getActivity(), R.string.toast_has_tag, Toast.LENGTH_SHORT).show();
        } else {
            tags.add(tag);
            getNewReturnDataIntent().putExtra(TAG, tag);
            mTagEditText.setText(null);
            getDialog().setTitle("+ #" + tag);
        }
    }
}
