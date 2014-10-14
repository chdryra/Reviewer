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

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;

/**
 * Dialog for editing tags.
 */
public class DialogTagEditFragment extends DialogCancelDeleteDoneFragment {
    private GVTagList.GVTag mCurrentTag;
    private ClearableAutoCompleteTextView mTagEditText;
    private IHTags          mTagsHandler;

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
        mTagEditText = (ClearableAutoCompleteTextView) v.findViewById(R.id.tag_edit_text);
        mTagEditText.setText(mCurrentTag.get());
        setKeyboardDoDoneOnEditText(mTagEditText);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTagsHandler = new IHTags();
        mCurrentTag = mTagsHandler.unpack(InputHandlerReviewData.CurrentNewDatum.CURRENT,
                getArguments());
        setDialogTitle(getResources().getString(R.string.edit) + " "
                + mTagsHandler.getGVType().getDatumString());
        setDeleteWhatTitle(mCurrentTag.get());
    }

    @Override
    protected void onDeleteButtonClick() {
        mTagsHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, mCurrentTag,
                getNewReturnDataIntent());
    }

    @Override
    protected boolean hasDataToDelete() {
        return true;
    }

    @Override
    protected void onDoneButtonClick() {
        Intent i = getNewReturnDataIntent();
        GVTagList.GVTag newTag = new GVTagList.GVTag(mTagEditText.getText().toString().trim());
        mTagsHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, mCurrentTag, i);
        mTagsHandler.pack(InputHandlerReviewData.CurrentNewDatum.NEW, newTag, i);
    }
}
