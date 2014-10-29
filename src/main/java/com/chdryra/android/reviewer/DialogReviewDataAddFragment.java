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

import com.chdryra.android.mygenerallibrary.DialogCancelAddDoneFragment;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * Base class for all dialogs that can add data to reviews.
 * <p>
 * This class handles mainly button presses and view initialisation. All other functionality is
 * outsourced to the appropriate classes:
 * <ul>
 * <li>UI updates and user input extraction: {@link com.chdryra.android.reviewer
 * .ViewHolderUI} object</li>
 * <li>Input validation and processing if not QUICKSET</li>: {@link com.chdryra.android
 * .reviewer.ReviewDataAddListener} object.
 * <li>Input validation and processing if QUICKSET: {@link com.chdryra.android.reviewer
 * .InputHandlerReviewData} object.</li>
 * <li>Updating reviews if QUICKSET: {@link com.chdryra.android.reviewer
 * .ControllerReviewEditable}</li> object
 * </ul>
 * </p>
 * <p>
 * By default the dialog won't add any data to reviews. It is assumed that data is updated using
 * a callback to the commissioning fragment.
 * It is then up to that fragment/activity to decide what to do
 * with the entered data. However, if the QUICK_SET boolean in the dialog arguments is set to
 * true, the dialog will validate using an {@link com.chdryra.android.reviewer
 * .InputHandlerReviewData} and forward the data directly to the ControllerReviewEditable packed in
 * the arguments by the Administrator.
 * </p>
 */
public abstract class DialogReviewDataAddFragment<T extends GVReviewDataList.GVReviewData>
        extends DialogCancelAddDoneFragment implements LaunchableUI {

    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    private final InputHandlerReviewData<T> mHandler;
    private       ControllerReviewEditable  mController;
    private       GVReviewDataList<T>       mData;
    private       UIHolder<T>               mDialogHolder;
    private       ReviewDataAddListener<T>  mAddListener;

    private boolean mQuickSet = false;

    DialogReviewDataAddFragment(GVType dataType) {
        this(new InputHandlerReviewData<T>(dataType));
    }

    DialogReviewDataAddFragment(InputHandlerReviewData<T> handler) {
        mHandler = handler;
    }

    /**
     * Provides a callback for when the add button is pressed
     *
     * @param <T>:{@link com.chdryra.android.reviewer.GVReviewDataList.GVReviewData} type
     */
    interface ReviewDataAddListener<T extends GVReviewDataList.GVReviewData> {
        boolean onReviewDataAdd(T data);
    }

    GVType getGVType() {
        return mHandler.getGVType();
    }

    @Override
    protected View createDialogUI() {
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
            if (mAddListener.onReviewDataAdd(newDatum)) updateDialogOnAdd(newDatum);
        }
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
    public void launch(LauncherUI launcher) {
        launcher.launch(this);
    }

    boolean isQuickSet() {
        return mQuickSet && mController != null;
    }

    T createGVDataFromInputs() {
        return mDialogHolder.getGVData();
    }

    void updateDialogOnAdd(T newDatum) {
        mDialogHolder.updateView(newDatum);
    }
}
