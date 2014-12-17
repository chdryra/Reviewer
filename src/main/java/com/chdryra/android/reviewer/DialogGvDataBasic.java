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
public abstract class DialogGvDataBasic<T extends GvDataList.GvData> implements DialogGvData<T> {
    protected GvDataViewHolderBasic<T> mViewHolder;
    private   int                      mEditTextId;

    DialogGvDataBasic(int layoutId, int[] viewIds, int editTextId, DialogGvDataAddFragment<T>
            dialogAdd, T nullData) {
        mEditTextId = editTextId;
        mViewHolder = new GvDataViewHolderBasic<>(layoutId, viewIds,
                new GvDataViewDialog<>(dialogAdd, new GvDataViewDialogAdapterAdd<>(this,
                        nullData)));
    }

    DialogGvDataBasic(int layoutId, int[] viewIds, int editTextId, DialogGvDataEditFragment<T>
            dialogEdit) {
        mEditTextId = editTextId;
        mViewHolder = new GvDataViewHolderBasic<>(layoutId, viewIds,
                new GvDataViewDialog<>(dialogEdit, new GvDataViewDialogAdapterEdit<>(this)));
    }

    @Override
    public EditText getEditTextForKeyboardAction() {
        return (EditText) mViewHolder.getView(mEditTextId);
    }

    @Override
    public GvDataViewHolder<T> getViewHolder() {
        return mViewHolder;
    }
}
