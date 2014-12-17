/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 October, 2014
 */

package com.chdryra.android.reviewer;

import android.widget.EditText;

/**
 * Created by: Rizwan Choudrey
 * On: 21/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * An extension of {@link GvDataViewHolderBasic} concerned with dialogs that add and edit review
 * data.
 * This is the fundamental add and edit DialogHolder for all {@link com.chdryra.android.reviewer
 * .GVReviewDataList.GVType}s except locations and URLs as they use activities for adding and
 * editing.
 * <p>
 * Combined the two dialog types (adding and editing) into this one
 * DialogHolder as not enough requirements in either to bother with 2 separate abstract classes.
 * Kind of a hotchpotch class using constructor overloading to separate the two but does the job.
 * </p>
 * <p/>
 * <p>
 * Provides a {@link com.chdryra.android.reviewer.GvDataViewDialog.GvDataViewDialogAdapter}
 * for adder Dialogs, and a different one for editor Dialogs.
 * </p>
 *
 * @param <T>: {@link GvDataList.GvData} type.
 */
class DialogHolderAddEdit<T extends GvDataList.GvData> extends GvDataViewHolderBasic<T> {
    private int mKeyboardEditText;

//    protected abstract String getDialogTitleOnAdd(T data);
//
//    protected abstract String getDeleteConfirmDialogTitle(T data);
//
//    protected abstract T createGvDataFromViews();
//
//    protected abstract void updateViews(T data);

    DialogHolderAddEdit(int layoutId, int[] viewIds, int keyboardEditText,
            DialogGvDataAddFragment<T> parent, final T nullData) {
        super(layoutId, viewIds);
        mKeyboardEditText = keyboardEditText;
        setGvDataView(new GvDataViewDialog<>(parent, new GvDataViewAddDialogAdapter(nullData)));
    }

    DialogHolderAddEdit(int layoutId, int[] viewIds, int keyboardEditText,
            DialogGvDataEditFragment<T> parent) {
        super(layoutId, viewIds);
        mKeyboardEditText = keyboardEditText;
        setGvDataView(new GvDataViewDialog<>(parent, new GvDataViewEditDialogAdapter()));
    }

    public EditText getEditTextForKeyboardAction() {
        return (EditText) getView(mKeyboardEditText);
    }

    class GvDataViewAddDialogAdapter implements GvDataViewDialog.GvDataViewDialogAdapter<T,
            DialogGvDataAddFragment<T>> {

        private T mNullData;

        public GvDataViewAddDialogAdapter(final T nullData) {
            mNullData = nullData;
        }

        @Override
        public void initialise(T data, DialogGvDataAddFragment<T> parentDialog) {
            parentDialog.setKeyboardDoActionOnEditText(getEditTextForKeyboardAction());
        }

        @Override
        public void update(T data, DialogGvDataAddFragment<T> parentDialog) {
            updateViews(mNullData);
            parentDialog.getDialog().setTitle("+ " + getDialogTitleOnAdd(data));
        }

        @Override
        public T getGvData() {
            return createGvDataFromViews();
        }
    }

    class GvDataViewEditDialogAdapter implements GvDataViewDialog.GvDataViewDialogAdapter<T,
            DialogGvDataEditFragment<T>> {

        @Override
        public void initialise(T data, DialogGvDataEditFragment<T> parentDialog) {
            parentDialog.setKeyboardDoDoneOnEditText(getEditTextForKeyboardAction());
            parentDialog.setDeleteWhatTitle(getDeleteConfirmDialogTitle(data));
            updateViews(data);
        }

        @Override
        public void update(T data, DialogGvDataEditFragment<T> parentDialog) {
        }

        @Override
        public T getGvData() {
            return createGvDataFromViews();
        }
    }
}