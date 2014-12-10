/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 October, 2014
 */

package com.chdryra.android.reviewer;


import com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment;

/**
 * Created by: Rizwan Choudrey
 * On: 17/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * Implements the {@link UIReviewData} part for
 * {@link com.chdryra.android.reviewer.DialogHolder}.
 * <p>
 * A helper class for {@link DialogHolder}. Uses a
 * {@link DialogHolderUI.DialogUIUpdater} object to fulfill the {@link com.chdryra.android
 * .reviewer.GVReviewDataUI} requirements. Binds the parent Dialog of the
 * DialogHolder to the updater as parts of the parent Dialog may also need to be updated
 * when presented with new data, for example the dialog title.
 * </p>
 * <p>The {@link DialogHolderUI.DialogUIUpdater} knows
 * how to initialise and update the content part of the UI encapsulated by the DialogHolder,
 * and knows how to extract review data from it. Each subclass of DialogHolder is expected
 * to be able to provide one of these to DialogHolder. See {@link com.chdryra
 * .android.reviewer.DialogHolderAddEdit} as an example.
 * </p>
 *
 * @param <T>: {@link GvDataList.GvData} type
 * @param <D>: {@link com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment}
 *             reference to the parent Dialog window that contains the DialogHolder UI.
 */
class DialogHolderUI<T extends GvDataList.GvData,
        D extends DialogCancelActionDoneFragment>
        implements UIReviewData<T> {

    private final D                     mDialog;
    private final DialogUIUpdater<T, D> mUpdater;

    /**
     * Defines the behaviour needed of a dialog updater that translates between the data within a
     * {@link GvDataList.GvData} object and the Dialog
     * updates that need to be performed.
     * <p>
     * Given {@link GvDataList.GvData} and a
     * parent Dialog window, need to be able to initialise and update the content UI (and
     * parent dialog if necessary), and extract review data from the UI.
     * </p>
     *
     * @param <T>
     * @param <D>
     */
    interface DialogUIUpdater<T extends GvDataList.GvData,
            D extends DialogCancelActionDoneFragment> {
        void initialise(T data, D parentDialog);

        void update(T data, D parentDialog);

        T getGVData();
    }

    DialogHolderUI(D parentDialog, DialogUIUpdater<T, D> updater) {
        mDialog = parentDialog;
        mUpdater = updater;
    }

    @Override
    public void initialiseView(T data) {
        mUpdater.initialise(data, mDialog);
    }

    @Override
    public void updateView(T data) {
        mUpdater.update(data, mDialog);
    }

    @Override
    public T getGVData() {
        return mUpdater.getGVData();
    }
}
