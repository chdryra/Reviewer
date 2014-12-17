/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 October, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.DialogCancelDeleteDoneFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Base class for all dialogs that can edit data on reviews.
 * <p>
 * This class handles mainly button presses and view intialisation. All other functionality is
 * outsourced to the appropriate classes:
 * <ul>
 * <li>UI updates and user input extraction: {@link com.chdryra.android.reviewer
 * .ViewHolderUI} object</li>
 * <li>Input validation and processing</li>: {@link com.chdryra.android
 * .reviewer.ReviewDataEditListener} object.
 * <li>Unpacking of received data: {@link com.chdryra.android.reviewer
 * .InputHandlerReviewData} object.</li>
 * </ul>
 * </p>
 */
public abstract class DialogGvDataEditFragment<T extends GvDataList.GvData>
        extends DialogCancelDeleteDoneFragment implements LaunchableUI {

    private T                     mDatum;
    private GvDataList.GvType     mDataType;
    private GvDataPacker<T>       mPacker;
    private GvDataViewHolder<T>   mUiHolder;
    private GvDataEditListener<T> mEditListener;

    /**
     * Provides a callback that can be called delete or done buttons are pressed.
     *
     * @param <T>:{@link GvDataList.GvData} type
     */
    public interface GvDataEditListener<T extends GvDataList.GvData> {
        void onGvDataDelete(T data);

        void onGvDataEdit(T oldDatum, T newDatum);
    }

    DialogGvDataEditFragment(Class<? extends GvDataList<T>> gvDataListClass) {
        mDataType = FactoryGvDataList.gvType(gvDataListClass);
        mPacker = new GvDataPacker<>();
    }

    @Override
    public void launch(LauncherUI launcher) {
        launcher.launch(this);
    }

    @Override
    protected View createDialogUI() {
        mUiHolder.inflate(getActivity());
        mUiHolder.initialiseView(mDatum);

        return mUiHolder.getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDialogTitle(getResources().getString(R.string.edit) + " " + mDataType.getDatumString());

        mDatum = mPacker.unpack(GvDataPacker.CurrentNewDatum.CURRENT, getArguments());
        mUiHolder = FactoryDialogHolder.newHolder(this);

        try {
            //TODO make type safe
            mEditListener = (GvDataEditListener<T>) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + " must implement " +
                    "GvDataEditListener");
        }
    }

    @Override
    protected void onConfirmedDeleteButtonClick() {
        mEditListener.onGvDataDelete(mDatum);
    }

    @Override
    protected boolean hasDataToDelete() {
        return mDatum != null;
    }

    @Override
    protected void onDoneButtonClick() {
        mEditListener.onGvDataEdit(mDatum, mUiHolder.getGvData());
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    GvDataList.GvType getGvType() {
        return mDataType;
    }

    T getCurrentGvData() {
        return mDatum;
    }
}
