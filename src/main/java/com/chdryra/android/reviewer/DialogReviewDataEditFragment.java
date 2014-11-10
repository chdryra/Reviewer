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
public abstract class DialogReviewDataEditFragment<T extends GVReviewDataList.GVReviewData>
        extends DialogCancelDeleteDoneFragment implements LaunchableUI {


    private final InputHandlerReviewData<T> mHandler;
    private final GVReviewDataList.GVType   mDataType;
    private       T                         mDatum;
    private       UIHolder<T>               mDialogHolder;
    private       ReviewDataEditListener<T> mListener;

    DialogReviewDataEditFragment(GVReviewDataList.GVType dataType) {
        this(dataType, new InputHandlerReviewData<T>(dataType));
    }

    DialogReviewDataEditFragment(GVReviewDataList.GVType dataType,
            InputHandlerReviewData<T> handler) {
        mDataType = dataType;
        mHandler = handler;
    }

    /**
     * Provides a callback that can be called delete or done buttons are pressed.
     *
     * @param <T>:{@link com.chdryra.android.reviewer.GVReviewDataList.GVReviewData} type
     */
    public interface ReviewDataEditListener<T extends GVReviewDataList.GVReviewData> {
        void onReviewDataDelete(T data);

        void onReviewDataEdit(T oldDatum, T newDatum);
    }

    @Override
    protected View createDialogUI() {
        mDialogHolder.inflate(getActivity());
        mDialogHolder.initialiseView(getDatum());

        return mDialogHolder.getView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatum = mHandler.unpack(InputHandlerReviewData.CurrentNewDatum.CURRENT,
                getArguments());
        mDialogHolder = FactoryDialogHolder.newDialogHolder(this);

        try {
            //TODO make type safe
            mListener = (ReviewDataEditListener<T>) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + " must implement " +
                    "reviewDataEditListener");
        }

        setDialogTitle(getResources().getString(R.string.edit) + " " + mHandler.getGVType()
                .getDatumString());
    }

    @Override
    protected void onConfirmedDeleteButtonClick() {
        mListener.onReviewDataDelete(createGVDataFromInputs());
    }

    @Override
    protected boolean hasDataToDelete() {
        return mDatum != null;
    }

    @Override
    public void launch(LauncherUI launcher) {
        launcher.launch(this);
    }

    @Override
    protected void onDoneButtonClick() {
        mListener.onReviewDataEdit(mDatum, createGVDataFromInputs());
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    T createGVDataFromInputs() {
        return mDialogHolder.getGVData();
    }

    GVReviewDataList.GVType getGVType() {
        return mDataType;
    }

    T getDatum() {
        return mDatum;
    }
}
