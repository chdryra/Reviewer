/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataViewDialogAdapterEdit<T extends GvDataList.GvData> implements GvDataViewDialog
        .GvDataViewDialogAdapter<T, DialogGvDataEditFragment<T>> {

    private DialogGvDataMethods<T> mDialog;

    public GvDataViewDialogAdapterEdit(DialogGvDataMethods<T> dialog) {
        mDialog = dialog;
    }

    @Override
    public void initialise(T data, DialogGvDataEditFragment<T> parentDialog) {
        parentDialog.setKeyboardDoDoneOnEditText(mDialog.getEditTextForKeyboardAction());
        parentDialog.setDeleteWhatTitle(mDialog.getDeleteConfirmDialogTitle(data));
        mDialog.updateViews(data);
    }

    @Override
    public void update(T data, DialogGvDataEditFragment<T> parentDialog) {
    }

    @Override
    public T getGvData() {
        return mDialog.createGvDataFromViews();
    }
}