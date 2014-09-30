/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

public class DialogTagAddFragment extends DialogAddReviewDataFragment {
    public static final String TAG = "com.chdryra.android.review.TAG";

    private GVTagList                     mTags;
    private ClearableAutoCompleteTextView mTagEditText;

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
        mTagEditText = (ClearableAutoCompleteTextView) v.findViewById(R.id.tag_edit_text);
        setKeyboardIMEDoAction(mTagEditText);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTags = (GVTagList) setAndInitData(GVType.TAGS);
        setDialogTitle(getResources().getString(R.string.dialog_add_tag_title));
    }

    @Override
    protected void OnAddButtonClick() {
        String tag = mTagEditText.getText().toString();
        if (tag == null || tag.length() == 0) {
            return;
        }

        if (mTags.contains(tag)) {
            Toast.makeText(getActivity(), R.string.toast_has_tag, Toast.LENGTH_SHORT).show();
        } else {
            mTags.add(tag);
            getNewReturnData().putExtra(TAG, tag);
            mTagEditText.setText(null);
            getDialog().setTitle("+ #" + tag);
        }
    }
}
