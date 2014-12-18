/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 December, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 17/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DialogGvData<T extends GvDataList.GvData> implements
        DialogGvDataMethods<T> {
    protected GvDataViewHolderBasic<T> mViewHolder;
    private   int                      mEditTextId;

    @Override
    public abstract String getDialogTitleOnAdd(T data);

    @Override
    public abstract String getDeleteConfirmDialogTitle(T data);

    @Override
    public EditText getEditTextForKeyboardAction() {
        return (EditText) mViewHolder.getView(mEditTextId);
    }

    @Override
    public abstract T createGvDataFromViews();

    @Override
    public abstract void updateViews(T data);

    @Override
    public GvDataViewHolder<T> getViewHolder() {
        return mViewHolder;
    }

    DialogGvData(int layoutId, int[] viewIds, int editTextId, DialogGvDataAddFragment<T>
            dialogAdd) {
        mEditTextId = editTextId;
        mViewHolder = new GvDataViewHolderBasic<>(layoutId, viewIds,
                new GvDataViewDialog<>(dialogAdd, new GvDataViewDialogAdapterAdd<>(this)));
    }

    DialogGvData(int layoutId, int[] viewIds, int editTextId, DialogGvDataEditFragment<T>
            dialogEdit) {
        mEditTextId = editTextId;
        mViewHolder = new GvDataViewHolderBasic<>(layoutId, viewIds,
                new GvDataViewDialog<>(dialogEdit, new GvDataViewDialogAdapterEdit<>(this)));
    }
}
