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

/**
 * A hotchpotch of methods required in order to use the same xml layout for both adding and
 * editing {@link com.chdryra.android.reviewer.GvDataList.GvData}
 *
 * @param <T>: {@link com.chdryra.android.reviewer.GvDataList.GvData} type
 */
public abstract class GvDataViewLayout<T extends GvDataList.GvData> {
    protected GvDataViewHolder<T> mViewHolder;
    private   int                 mEditTextId;

    public abstract String getTitleOnAdd(T data);

    public abstract String getDeleteConfirmDialogTitle(T data);

    public abstract T createGvDataFromViews();

    public abstract void updateViews(T data);

    //public abstract void clearViews();

    GvDataViewLayout(int layoutId, int[] viewIds, int keyboardEditTextId,
            GvDataViewAdd.GvDataAdder<T> adder) {
        mEditTextId = keyboardEditTextId;
        GvDataView<T> view = new GvDataViewAdd<>(adder, this);
        mViewHolder = new GvDataViewHolder<>(layoutId, viewIds, view);
    }

    GvDataViewLayout(int layoutId, int[] viewIds, int keyboardEditTextId,
            GvDataViewEdit.GvDataEditor<T> editor) {
        mEditTextId = keyboardEditTextId;
        GvDataView<T> view = new GvDataViewEdit<>(editor, this);
        mViewHolder = new GvDataViewHolder<>(layoutId, viewIds, view);
    }

    public EditText getEditTextForKeyboardAction() {
        return (EditText) mViewHolder.getView(mEditTextId);
    }

    public GvDataViewHolder<T> getViewHolder() {
        return mViewHolder;
    }
}
