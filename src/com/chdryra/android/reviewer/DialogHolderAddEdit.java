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
abstract class DialogHolderAddEdit<T extends GVReviewDataList.GVReviewData> extends
        DialogHolderBasic<T> {

    DialogHolderAddEdit(int layoutId, int[] viewIds, DialogReviewDataAddFragment<T> dialogAdd,
                        final T nullData) {
        super(layoutId, viewIds);
        setDialogUI(new DialogUI<T, DialogReviewDataAddFragment<T>>(dialogAdd,
                getReviewDataAddManager(nullData)));
    }

    DialogHolderAddEdit(int layoutId, int[] viewIds, DialogReviewDataEditFragment<T> dialogEdit) {
        super(layoutId, viewIds);
        setDialogUI(new DialogUI<T, DialogReviewDataEditFragment<T>>(dialogEdit,
                getReviewDataEditManager()));
    }

    protected abstract EditText getEditTextForKeyboardAction();

    protected abstract String getDialogOnAddTitle(T data);

    protected abstract String getDialogDeleteConfirmTitle(T data);

    protected abstract T createGVData();

    protected abstract void updateInputs(T fact);

    private DialogUI.DialogUIManager<T, DialogReviewDataAddFragment<T>> getReviewDataAddManager
            (final T nullData) {
        return new DialogUI.DialogUIManager<T, DialogReviewDataAddFragment<T>>() {

            @Override
            public void initialise(T data, DialogReviewDataAddFragment<T> dialog) {
                dialog.setKeyboardDoActionOnEditText(getEditTextForKeyboardAction());
            }

            @Override
            public void update(T data, DialogReviewDataAddFragment<T> dialog) {
                updateInputs(nullData);
                dialog.getDialog().setTitle("+ " + getDialogOnAddTitle(data));
            }

            @Override
            public T getGVData() {
                return createGVData();
            }
        };
    }

    private DialogUI.DialogUIManager<T, DialogReviewDataEditFragment<T>> getReviewDataEditManager
            () {
        return new DialogUI.DialogUIManager<T, DialogReviewDataEditFragment<T>>() {

            @Override
            public void initialise(T data, DialogReviewDataEditFragment<T> dialog) {
                updateInputs(data);
                dialog.setKeyboardDoDoneOnEditText(getEditTextForKeyboardAction());
                dialog.setDeleteWhatTitle(getDialogDeleteConfirmTitle(data));
            }

            @Override
            public void update(T data, DialogReviewDataEditFragment<T> dialog) {
            }

            @Override
            public T getGVData() {
                return createGVData();
            }
        };
    }
}