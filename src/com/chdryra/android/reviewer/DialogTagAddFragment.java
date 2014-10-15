/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.myandroidwidgets.ClearableAutoCompleteTextView;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;
import com.chdryra.android.reviewer.GVTagList.GVTag;

/**
 * Dialog for adding tags.
 */
public class DialogTagAddFragment extends DialogAddReviewDataFragment {
    private ClearableAutoCompleteTextView mTagEditText;
    private InputHandlerReviewData<GVTag> mInputHandler;

    public DialogTagAddFragment() {
        super(GVType.TAGS);
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_tag, null);
        mTagEditText = (ClearableAutoCompleteTextView) v.findViewById(R.id.tag_edit_text);
        setKeyboardDoActionOnEditText(mTagEditText);
        //TODO move creation of input handler to commissioning fragment to pass correct data.
        mInputHandler = new IHTags((GVTagList) getData());

        return v;
    }

    @Override
    protected void onAddButtonClick() {
        GVTag tag = createGVData();
        if (mInputHandler.isNewAndValid(tag, getActivity())) {
            Intent data = createNewReturnData();
            mInputHandler.pack(InputHandlerReviewData.CurrentNewDatum.NEW, tag, data);
            mInputHandler.add(data, getActivity());
            resetDialog();
            setDialogAddedTitle(tag);
        }
    }

    GVTag createGVData() {
        return new GVTag(mTagEditText.getText().toString().trim());
    }

    void resetDialog() {
        mTagEditText.setText(null);
    }

    void setDialogAddedTitle(GVTag tag) {
        getDialog().setTitle("+ #" + tag.get());
    }
}
