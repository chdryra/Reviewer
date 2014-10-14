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

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * Base class for all dialogs that can add data to reviews.
 * <p>
 *     Need to override <code>createDialogUI(.)</code> to define the dialog's UI,
 *     and <code>onAddButtonClick()</code> to specify the action when the add button is pressed.
 * </p>
 * <p>
 * By default the dialog won't add any data to reviews. It is assumed that implementations
 * will update the return data intent to forward any data entered to the fragment or activity
 * that commissioned the dialog. It is then up to that fragment/activity to decide what to do
 * with the entered data. However, if the QUICK_SET boolean in the dialog arguments is set to
 * true, the dialog will forward the data directly to the ControllerReviewEditable packed in
 * the arguments by the Administrator.
 * </p>
 */
public abstract class DialogAddReviewDataFragment extends DialogCancelAddDoneFragment {
    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    private GVType mDataType;
    private ControllerReviewEditable mController;
    private GVReviewDataList<? extends GVData> mData;
    private boolean mQuickSet = false;

    protected DialogAddReviewDataFragment(GVType dataType) {
        mDataType = dataType;
    }

    @Override
    protected abstract View createDialogUI(ViewGroup parent);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuickSet = getArguments().getBoolean(QUICK_SET);
        mController = (ControllerReviewEditable) Administrator.get(getActivity()).unpack
                (getArguments());

        mData = mController.getData(mDataType);
        setDialogTitle(getResources().getString(R.string.add) + " " + mDataType.getDatumString());
    }

    @Override
    protected abstract void onAddButtonClick();

    GVReviewDataList<? extends GVData> getData() {
        return mData;
    }

    @Override
    protected void onDoneButtonClick() {
        if (mQuickSet && mController != null) mController.setData(mData);
    }
}
