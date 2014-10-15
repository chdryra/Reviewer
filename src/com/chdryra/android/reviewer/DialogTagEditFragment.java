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
    private GVTagList.GVTag                         mDatum;
    private ClearableAutoCompleteTextView           mTagEditText;
    private InputHandlerReviewData<GVTagList.GVTag> mHandler;

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
        mTagEditText = (ClearableAutoCompleteTextView) v.findViewById(R.id.tag_edit_text);
        mTagEditText.setText(mDatum.get());
        setKeyboardDoDoneOnEditText(mTagEditText);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new IHTags();
        mDatum = mHandler.unpack(InputHandlerReviewData.CurrentNewDatum.CURRENT,
                getArguments());
        setDialogTitle(getResources().getString(R.string.edit) + " " + mHandler.getGVType()
                .getDatumString());
        setDeleteWhatTitle(mDatum.get());
    }

    @Override
    protected void onDeleteButtonClick() {
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, mDatum,
                createNewReturnData());
    }

    @Override
    protected boolean hasDataToDelete() {
        return true;
    }

    @Override
    protected void onDoneButtonClick() {
        Intent data = createNewReturnData();
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, mDatum, data);
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.NEW, createGVData(), data);
    }

    protected GVTagList.GVTag createGVData() {
        return new GVTagList.GVTag(mTagEditText.getText().toString().trim());
    }
}
