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
public abstract class DialogGvDataAddFragment<T extends GvDataList.GvData> extends
        DialogCancelAddDoneFragment implements LaunchableUI {

    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    private InputHandlerGvData<T>    mHandler;
    private ControllerReviewEditable mController;
    private GvDataList<T>            mData;
    private GvDataUiHolder<T>        mDialogHolder;
    private GvDataAddListener<T>     mAddListener;

    private boolean mQuickSet = false;

    /**
     * Provides a callback for when the add button is pressed
     *
     * @param <T>:{@link GvDataList.GvData} type
     */
    interface GvDataAddListener<T extends GvDataList.GvData> {
        boolean onGvDataAdd(T data);
    }

    DialogGvDataAddFragment(Class<? extends GvDataList<T>> gvDataListClass) {
        try {
            mData = gvDataListClass.newInstance();
        } catch (java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create class " + gvDataListClass.getName());
        }
    }

    @Override
    public void launch(LauncherUI launcher) {
        launcher.launch(this);
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
            mData = mController.getData(getGvType());
        }

        mHandler = InputHandlerFactory.newInputHandler(mData);

        if (!isQuickSet()) {
            try {
                //TODO make type safe
                mAddListener = (GvDataAddListener<T>) getTargetFragment();
            } catch (ClassCastException e) {
                throw new ClassCastException(getTargetFragment().toString() + " must implement " +
                        "reviewDataAddListener");
            }
        }

        setDialogTitle(getResources().getString(R.string.add) + " " + getGvType().getDatumString());
        mDialogHolder = FactoryDialogHolder.newDialogHolder(this);
    }

    @Override
    protected void onAddButtonClick() {
        T newDatum = mDialogHolder.getGvData();

        if (isQuickSet()) {
            if (mHandler.add(newDatum, getActivity())) mDialogHolder.updateView(newDatum);
        } else {
            if (mAddListener.onGvDataAdd(newDatum)) mDialogHolder.updateView(newDatum);
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

    GvDataList.GvType getGvType() {
        return mData.getGvType();
    }

    boolean isQuickSet() {
        return mQuickSet && mController != null;
    }
}
