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
public class GvDataViewDialogAdapterAdd<T extends GvDataList.GvData> implements GvDataViewDialog
        .GvDataViewDialogAdapter<T, DialogGvDataAddFragment<T>> {

    private DialogGvDataMethods<T> mDialog;

    public GvDataViewDialogAdapterAdd(DialogGvDataMethods<T> dialog) {
        mDialog = dialog;
    }

    @Override
    public void initialise(T data, DialogGvDataAddFragment<T> parentDialog) {
        parentDialog.setKeyboardDoActionOnEditText(mDialog.getEditTextForKeyboardAction());
    }

    @Override
    public void update(T data, DialogGvDataAddFragment<T> parentDialog) {
        mDialog.updateViews(parentDialog.getNullingItem());
        parentDialog.getDialog().setTitle("+ " + mDialog.getDialogTitleOnAdd(data));
    }

    @Override
    public T getGvData() {
        return mDialog.createGvDataFromViews();
    }
}
