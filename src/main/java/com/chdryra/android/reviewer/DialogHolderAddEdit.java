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
 * An extension of {@link DialogHolder} concerned with dialogs that add and edit review data.
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
 * Provides a {@link com.chdryra.android.reviewer.DialogHolderUI.DialogUIUpdater}
 * for adder Dialogs, and a different one for editor Dialogs.
 * </p>
 *
 * @param <T>: {@link GVDataList.GvData} type.
 */
abstract class DialogHolderAddEdit<T extends GVDataList.GvData> extends
        DialogHolder<T> {

    private final UIReviewData<T> mDialogUI;

    protected abstract EditText getEditTextForKeyboardAction();

    protected abstract String getDialogOnAddTitle(T data);

    protected abstract String getDialogDeleteConfirmTitle(T data);

    protected abstract T createGVData();

    protected abstract void updateWithGVData(T data);

    DialogHolderAddEdit(int layoutId, int[] viewIds, DialogReviewDataAddFragment<T> parent,
            final T nullData) {
        super(layoutId, viewIds);
        mDialogUI = new DialogHolderUI<T, DialogReviewDataAddFragment<T>>(parent,
                getReviewDataAddUI
                        (nullData));
    }

    DialogHolderAddEdit(int layoutId, int[] viewIds, DialogReviewDataEditFragment<T>
            parent) {
        super(layoutId, viewIds);
        mDialogUI = new DialogHolderUI<T, DialogReviewDataEditFragment<T>>(parent,
                getReviewDataEditUI());
    }

    @Override
    protected UIReviewData<T> getGVReviewDataUI() {
        return mDialogUI;
    }

    private DialogHolderUI.DialogUIUpdater<T, DialogReviewDataAddFragment<T>> getReviewDataAddUI
            (final T nullData) {
        return new DialogHolderUI.DialogUIUpdater<T, DialogReviewDataAddFragment<T>>() {

            @Override
            public void initialise(T data, DialogReviewDataAddFragment<T> parentDialog) {
                parentDialog.setKeyboardDoActionOnEditText(getEditTextForKeyboardAction());
            }

            @Override
            public void update(T data, DialogReviewDataAddFragment<T> parentDialog) {
                updateWithGVData(nullData);
                parentDialog.getDialog().setTitle("+ " + getDialogOnAddTitle(data));
            }

            @Override
            public T getGVData() {
                return createGVData();
            }
        };
    }

    private DialogHolderUI.DialogUIUpdater<T, DialogReviewDataEditFragment<T>> getReviewDataEditUI
            () {
        return new DialogHolderUI.DialogUIUpdater<T, DialogReviewDataEditFragment<T>>() {

            @Override
            public void initialise(T data, DialogReviewDataEditFragment<T> parentDialog) {
                updateWithGVData(data);
                parentDialog.setKeyboardDoDoneOnEditText(getEditTextForKeyboardAction());
                parentDialog.setDeleteWhatTitle(getDialogDeleteConfirmTitle(data));
            }

            @Override
            public void update(T data, DialogReviewDataEditFragment<T> parentDialog) {
            }

            @Override
            public T getGVData() {
                return createGVData();
            }
        };
    }
}