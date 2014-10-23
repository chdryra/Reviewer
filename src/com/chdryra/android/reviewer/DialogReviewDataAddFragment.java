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
        DialogCancelAddDoneFragment implements ReviewDataAdder<T> {
    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    protected InputHandlerReviewData<T> mHandler;
    private   ControllerReviewEditable  mController;
    private   GVReviewDataList<T>       mData;
    private   DialogHolder<T>           mDialogHolder;
    private   ReviewDataAddListener<T>  mAddListener;

    private boolean mQuickSet = false;

    protected DialogReviewDataAddFragment(GVType dataType) {
        mHandler = new InputHandlerReviewData<T>(dataType);
    }

    GVType getGVType() {
        return mHandler.getGVType();
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

        if (mController != null) {
            //TODO make type safe
            mData = (GVReviewDataList<T>) mController.getData(getGVType());
            mHandler.setData(mData);
        }

        if (!isQuickSet()) {
            try {
                //TODO make type safe
                mAddListener = (ReviewDataAddListener<T>) getTargetFragment();
            } catch (ClassCastException e) {
                throw new ClassCastException(getTargetFragment().toString() + " must implement " +
                        "reviewDataAddListener");
            }
        }

        setDialogTitle(getResources().getString(R.string.add) + " " + getGVType().getDatumString());
        mDialogHolder = FactoryDialogHolder.newDialogHolder(this);
    }

    @Override
    protected void onAddButtonClick() {
        T newDatum = createGVDataFromInputs();

        if (isQuickSet()) {
            if (mHandler.add(newDatum, getActivity())) updateDialogOnAdd(newDatum);
        } else {
            reviewDataAdd(mAddListener, newDatum);
        }
    }

    @Override
    public void reviewDataAdd(ReviewDataAddListener<T> listener, T data) {
        if (listener.onReviewDataAdd(data)) updateDialogOnAdd(data);
    }

    @Override
    protected void onDoneButtonClick() {
        if (isQuickSet()) mController.setData(mData);
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    @Override
    public void launch(ReviewDataUILauncher launcher) {
        launcher.launch(this);
    }

    boolean isQuickSet() {
        return mQuickSet && mController != null;
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
