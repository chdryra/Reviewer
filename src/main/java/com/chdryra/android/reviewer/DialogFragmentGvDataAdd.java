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
import android.widget.EditText;

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
public abstract class DialogFragmentGvDataAdd<T extends GvDataList.GvData> extends
        DialogCancelAddDoneFragment implements GvDataViewAdd.GvDataAdder, LaunchableUi {

    public static final String QUICK_SET = "com.chdryra.android.reviewer.dialog_quick_mode";

    private ReviewBuilder.DataBuilder mBuilder;
    private GvDataList<T>             mData;
    private GvDataViewHolder<T>       mViewHolder;
    private GvDataHandler<T>          mHandler;
    private GvDataAddListener<T>      mAddListener;

    private boolean mQuickSet = false;

    /**
     * Provides a callback for when the add button is pressed
     *
     * @param <T>:{@link GvDataList.GvData} type
     */
    public interface GvDataAddListener<T extends GvDataList.GvData> {
        boolean onGvDataAdd(T data);
    }

    DialogFragmentGvDataAdd(Class<? extends GvDataList<T>> gvDataListClass) {
        mData = FactoryGvData.newList(gvDataListClass);
        mViewHolder = FactoryGvDataViewHolder.newViewHolder(getGvType(), this);
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(this);
    }

    @Override
    public void setKeyboardAction(EditText editText) {
        setKeyboardDoActionOnEditText(editText);
        editText.setImeActionLabel("Add", KEYBOARD_DO_ACTION);
    }

    @Override
    public void setTitle(String title) {
        getDialog().setTitle(title);
    }

    public GvDataList.GvType getGvType() {
        return mData.getGvType();
    }

    @Override
    protected View createDialogUi() {
        mViewHolder.inflate(getActivity());
        mViewHolder.initialiseView(null);

        return mViewHolder.getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mQuickSet = args != null && args.getBoolean(QUICK_SET);
        mBuilder = (ReviewBuilder.DataBuilder) Administrator.get(getActivity()).getReviewBuilder()
                .getDataBuilder(mData.getGvType());

        //TODO make type safe
        if (mBuilder != null) mData = mBuilder.getGridData();
        mHandler = FactoryGvDataHandler.newHandler(mData);

        //TODO make type safe
        if (!isQuickSet()) {
            mAddListener = (GvDataAddListener<T>) getTargetListener(GvDataAddListener.class);
        }

        setDialogTitle(getResources().getString(R.string.add) + " " + getGvType().getDatumString());
    }

    @Override
    protected void onAddButtonClick() {
        T newDatum = mViewHolder.getGvData();

        boolean added = isQuickSet() ? mHandler.add(newDatum, getActivity()) :
                newDatum.isValidForDisplay() && mAddListener.onGvDataAdd(newDatum);

        if (added) mViewHolder.updateView(newDatum);
    }

    @Override
    protected void onDoneButtonClick() {
        if (isQuickSet()) mBuilder.setData(mData);
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    boolean isQuickSet() {
        return mQuickSet && mBuilder != null;
    }
}
