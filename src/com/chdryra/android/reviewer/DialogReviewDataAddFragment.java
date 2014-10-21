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

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * Base class for all dialogs that can add data to reviews.
 * <p>
 * Need to override <code>createDialogUI(.)</code> to define the dialog's UI,
 * and <code>onAddButtonClick()</code> to specify the action when the add button is pressed.
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
public abstract class DialogReviewDataAddFragment<T extends GVReviewDataList.GVReviewData> extends
        DialogCancelAddDoneFragment {
    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    private GVType                    mDataType;
    private ControllerReviewEditable  mController;
    private GVReviewDataList<T>       mData;
    private InputHandlerReviewData<T> mHandler;
    private DialogHolder<T> mDialogHolder;
    private boolean mQuickSet = false;

    protected DialogReviewDataAddFragment(GVType dataType) {
        mDataType = dataType;
    }

    GVType getGVType() {
        return mDataType;
    }

    @Override
    protected View createDialogUI(ViewGroup parent) {
        mDialogHolder.inflate(getActivity());
        mDialogHolder.initialiseView(null);

        return mDialogHolder.getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuickSet = getArguments().getBoolean(QUICK_SET);
        mController = (ControllerReviewEditable) Administrator.get(getActivity()).unpack
                (getArguments());

        //TODO move creation of input handler to commissioning fragment to pass correct data.
        mHandler = new InputHandlerReviewData<T>(mDataType);
        if (mController != null) {
            //TODO make typesafe
            mData = (GVReviewDataList<T>) mController.getData(mDataType);
            mHandler.setData(mData);
        }

        setDialogTitle(getResources().getString(R.string.add) + " " + mDataType.getDatumString());
        mDialogHolder = FactoryDialogHolder.newDialogHolder(this);
    }

    @Override
    protected void onAddButtonClick() {
        T newDatum = createGVDataFromInputs();
        if (mHandler.isNewAndValid(newDatum, getActivity())) {
            Intent data = createNewReturnData();
            mHandler.pack(InputHandlerReviewData.CurrentNewDatum.NEW, newDatum, data);
            mHandler.add(data, getActivity());
            updateDialogOnAdd(newDatum);
        }
    }

    @Override
    protected void onDoneButtonClick() {
        if (mQuickSet && mController != null) mController.setData(mData);
    }

    protected T createGVDataFromInputs() {
        return mDialogHolder.getGVData();
    }

    protected void updateDialogOnAdd(T newDatum) {
        mDialogHolder.updateView(newDatum);
    }

    GVReviewDataList<T> getData() {
        return mData;
    }
}
