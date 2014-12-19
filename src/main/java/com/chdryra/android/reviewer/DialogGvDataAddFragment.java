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
 * Base class for all dialog fragments that can edit data on reviews.
 * <p>
 * This class is the launched dialog and handles mainly button presses,
 * view intialisation and callbacks to the commissioning fragment. All
 * other functionality is outsourced to the appropriate classes:
 * <ul>
 * <li>{@link GvDataPacker}: Unpacking of received data.</li>
 * <li>{@link GvDataViewHolder}: UI updates and user input extraction</li>
 * <li>{@link GvDataAddListener}: commissioning fragment.
 * <li>{@link GvDataHandler}: input validation when QUICK_SET = true.
 * </ul>
 * </p>
 * <p>
 * By default the dialog won't add any data to reviews. It is assumed that data is updated using
 * a callback to the commissioning fragment.
 * It is then up to that fragment/activity to decide what to do
 * with the entered data. However, if the QUICK_SET boolean in the dialog arguments is set to
 * true, the dialog will validate using a {@link GvDataHandler} and forward the data directly to the
 * ControllerReviewEditable packed in the arguments by the Administrator.
 * </p>
 */
public abstract class DialogGvDataAddFragment<T extends GvDataList.GvData> extends
        DialogCancelAddDoneFragment implements LaunchableUI {

    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    private ControllerReviewEditable mController;
    private GvDataList<T>            mData;
    private GvDataViewHolder<T>      mViewHolder;
    private GvDataHandler<T>         mHandler;
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
        mData = FactoryGvData.newList(gvDataListClass);
        mViewHolder = FactoryGvDataViewHolder.newViewHolder(this);
    }

    public T getNullingItem() {
        return mData.getNullItem();
    }

    @Override
    public void launch(LauncherUI launcher) {
        launcher.launch(this);
    }

    @Override
    protected View createDialogUI() {
        mViewHolder.inflate(getActivity());
        mViewHolder.initialiseView(getNullingItem());

        return mViewHolder.getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mQuickSet = args != null && args.getBoolean(QUICK_SET);
        mController = (ControllerReviewEditable) Administrator.get(getActivity()).unpack(args);

        //TODO make type safe
        if (mController != null) mData = mController.getData(getGvType());
        mHandler = FactoryGvDataHandler.newHandler(mData);

        if (!isQuickSet()) {
            try {
                //TODO make type safe
                mAddListener = (GvDataAddListener<T>) getTargetFragment();
            } catch (ClassCastException e) {
                throw new ClassCastException(getTargetFragment().toString() + " must implement " +
                        "GvDataAddListener");
            }
        }

        setDialogTitle(getResources().getString(R.string.add) + " " + getGvType().getDatumString());
    }

    @Override
    protected void onAddButtonClick() {
        T newDatum = mViewHolder.getGvData();

        boolean added = isQuickSet() ? mHandler.add(newDatum, getActivity()) : mAddListener
                .onGvDataAdd(newDatum);

        if (added) mViewHolder.updateView(newDatum);
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
