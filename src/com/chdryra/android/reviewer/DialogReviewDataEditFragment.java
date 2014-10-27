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
import android.view.ViewGroup;

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
public abstract class DialogReviewDataEditFragment<T extends GVReviewDataList.GVReviewData> extends
                                                                                            DialogCancelDeleteDoneFragment implements ReviewDataEditor<T> {
    protected InputHandlerReviewData<T> mHandler;
    private   GVReviewDataList.GVType   mDataType;
    private   T                         mDatum;
    private   ViewHolderUI<T>           mDialogHolder;
    private   ReviewDataEditListener<T> mListener;

    protected DialogReviewDataEditFragment(GVReviewDataList.GVType dataType) {
        mDataType = dataType;
        mHandler = new InputHandlerReviewData<T>(mDataType);
    }

    protected View createDialogUI(ViewGroup parent) {
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
            mListener = (ReviewDataEditListener<T>) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() + " must implement " +
                                         "reviewDataEditListener");
        }

        setDialogTitle(getResources().getString(R.string.edit) + " " + mHandler.getGVType()
                                                                               .getDatumString());
    }

    @Override
    protected void onDeleteButtonClick() {
        reviewDataDelete(mListener, createGVData());
    }

    @Override
    protected boolean hasDataToDelete() {
        return mDatum != null;
    }

    @Override
    public void launch(ReviewDataUILauncher launcher) {
        launcher.launch(this);
    }

    @Override
    public void reviewDataDelete(ReviewDataEditListener<T> listener, T data) {
        listener.onReviewDataDelete(data);
    }

    @Override
    public void reviewDataEdit(ReviewDataEditListener<T> listener, T oldDatum, T newDatum) {
        listener.onReviewDataEdit(oldDatum, newDatum);
    }

    @Override
    protected void onDoneButtonClick() {
        reviewDataEdit(mListener, mDatum, createGVData());
    }

    @Override
    protected Intent getReturnData() {
        return null;
    }

    protected T createGVData() {
        return mDialogHolder.getGVData();
    }

    protected GVReviewDataList.GVType getGVType() {
        return mDataType;
    }

    protected T getDatum() {
        return mDatum;
    }
}
