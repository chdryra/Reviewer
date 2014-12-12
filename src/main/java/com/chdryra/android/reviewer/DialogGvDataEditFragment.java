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

    private GvDataList.GvType     mDataType;
    private InputHandlerGvData<T> mUnpacker;
    private T                     mDatum;
    private GvDataUiHolder<T>     mDialogHolder;
    private GvDataEditListener<T> mListener;

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
        GvDataList<T> data;
        mDataType = null;
        try {
            //Need to do this just to get correct GvType....
            data = gvDataListClass.newInstance();
            mDataType = data.getGvType();
        } catch (java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't create class " + gvDataListClass.getName());
        }

        mUnpacker = InputHandlerFactory.newInputHandler(gvDataListClass);
    }

    @Override
    public void launch(LauncherUI launcher) {
        launcher.launch(this);
    }

    @Override
    protected View createDialogUI() {
        mDialogHolder.inflate(getActivity());
        mDialogHolder.initialiseView(getGvData());

        return mDialogHolder.getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogTitle(getResources().getString(R.string.edit) + " " + mDataType.getDatumString());
        mDatum = mUnpacker.unpack(InputHandlerGvData.CurrentNewDatum.CURRENT, getArguments());
        mDialogHolder = FactoryDialogHolder.newDialogHolder(this);

        try {
            //TODO make type safe
            mListener = (GvDataEditListener<T>) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + " must implement " +
                    "GvDataEditListener");
        }
    }

    @Override
    protected void onConfirmedDeleteButtonClick() {
        mListener.onGvDataDelete(mDatum);
    }

    @Override
    protected boolean hasDataToDelete() {
        return mDatum != null;
    }

    @Override
    protected void onDoneButtonClick() {
        mListener.onGvDataEdit(mDatum, mDialogHolder.getGvData());
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    GvDataList.GvType getGvType() {
        return mDataType;
    }

    T getGvData() {
        return mDatum;
    }
}
