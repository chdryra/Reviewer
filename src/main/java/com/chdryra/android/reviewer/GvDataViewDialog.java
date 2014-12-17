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
 * Implements {@link GvDataView} for dialogs.
 * <p>
 * A helper class for {@link GvDataViewHolderBasic}. Uses a
 * {@link com.chdryra.android.reviewer.GvDataViewDialog.GvDataViewDialogAdapter} object to
 * fulfill the
 * {@link GvDataView} requirements. Binds the parent Dialog to the adapter as parts of the parent
 * Dialog may also need to be updated
 * when presented with new data, for example the dialog title.
 * </p>
 * <p>The {@link com.chdryra.android.reviewer.GvDataViewDialog.GvDataViewDialogAdapter} knows
 * how to initialise and update the content part of the Dialog,
 * and knows how to extract data from it. Each subclass of {@link GvDataViewHolderBasic} is expected
 * to be able to provide one of these. See {@link com.chdryra
 * .android.reviewer.DialogHolderAddEdit} as an example.
 * </p>
 *
 * @param <T>: {@link GvDataList.GvData} type
 * @param <D>: {@link com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment}
 *             reference to the parent Dialog.
 */
class GvDataViewDialog<T extends GvDataList.GvData, D extends DialogCancelActionDoneFragment>
        implements GvDataView<T> {

    private final D                     mDialog;
    private final GvDataViewDialogAdapter<T, D> mAdapter;

    /**
     * Defines  a dialog interface that given some
     * {@link GvDataList.GvData} object can update the ui, or can return some data when requested.
     * <p>
     * Given {@link GvDataList.GvData} and a
     * parent Dialog window, need to be able to initialise and update the content UI (and
     * parent dialog if necessary), and extract review data from the UI.
     * </p>
     *
     * @param <T> {@link GvDataList.GvData} type
     * @param <D> {@link com.chdryra.android.mygenerallibrary.DialogCancelActionDoneFragment}
     *             reference to the parent Dialog.
     */
    interface GvDataViewDialogAdapter<T extends GvDataList.GvData,
            D extends DialogCancelActionDoneFragment> {
        void initialise(T data, D parentDialog);

        void update(T data, D parentDialog);

        T getGvData();
    }

    GvDataViewDialog(D parentDialog, GvDataViewDialogAdapter<T, D> adapter) {
        mDialog = parentDialog;
        mAdapter = adapter;
    }

    @Override
    public void initialiseView(T data) {
        mAdapter.initialise(data, mDialog);
    }

    @Override
    public void updateView(T data) {
        mAdapter.update(data, mDialog);
    }

    @Override
    public T getGvData() {
        return mAdapter.getGvData();
    }
}
