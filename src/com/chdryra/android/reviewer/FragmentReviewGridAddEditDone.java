/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.GVData;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: base class for adding/editing review data UIs.
 * <p/>
 * <p>
 * By default assumes the banner button will launch an Add dialog and a grid cell click will
 * launch an edit dialog. These dialogs can be set using <code>setAddEditDialogs(.)</code>. If
 * using this default then need to implement <code>packGridCellData(.)</code> to bundle the
 * grid cell selected data for editing so that it can be passed to the edit dialog.
 * </p>
 * <p/>
 * <p>
 * Need to implement <code>addData(.)</code> and <code>editData(.)</code> to know what to do
 * once the appropriate dialog or activity has returned the user input.
 * </p>
 *
 * @param <T>
 */
public abstract class FragmentReviewGridAddEditDone<T extends GVData> extends
        FragmentReviewGrid<T> {
    private final static String TAG           = "FragmentReviewGridAddEditDone";
    private final static String DATA_ADD_TAG  = "DataAddTag";
    private final static String DATA_EDIT_TAG = "DataEditTag";

    private final static int DATA_ADD  = 10;
    private final static int DATA_EDIT = 11;

    private Class<? extends DialogFragment> mAddDialogClass;
    private Class<? extends DialogFragment> mEditDialogClass;

    protected abstract void addData(int resultCode, Intent data);

    protected abstract void editData(int resultCode, Intent data);

    protected abstract Bundle packGridCellData(T data, Bundle args);

    @SuppressWarnings("unchecked")
    GVReviewDataList<T> setAndInitData(GVType dataType) {
        //Not sure how to make sure this setup is type safe
        GVReviewDataList<T> data = (GVReviewDataList<T>) getController().getData(dataType);
        setGridViewData(data);
        return data;
    }

    protected void setAddEditDialogs(Class<? extends DialogFragment> addDialog,
                                     Class<? extends DialogFragment> editDialog) {
        mAddDialogClass = addDialog;
        mEditDialogClass = editDialog;
    }

    protected final int getRequestCode(DataAddEdit addOrEdit) {
        return addOrEdit.getRequestCode();
    }

    protected String getRequestTag(DataAddEdit addOrEdit) {
        return addOrEdit.getTag();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        switch (requestCode) {
            case DATA_ADD:
                addData(resultCode, data);
                break;
            case DATA_EDIT:
                editData(resultCode, data);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

        updateUI();
    }

    @Override
    protected void onBannerButtonClick() {
        if (mAddDialogClass == null) return;

        DialogShower.show(getDialogFragment(mAddDialogClass), FragmentReviewGridAddEditDone.this,
                getRequestCode(DataAddEdit.ADD), getRequestTag(DataAddEdit.ADD),
                Administrator.get(getActivity()).pack(getController()));
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        if (mEditDialogClass == null) return;

        T reviewData = (T) parent.getItemAtPosition(position);

        DialogShower.show(getDialogFragment(mEditDialogClass), FragmentReviewGridAddEditDone.this,
                getRequestCode(DataAddEdit.EDIT), getRequestTag(DataAddEdit.EDIT),
                packGridCellData(reviewData, Administrator.get(getActivity()).pack(getController
                        ())));
    }

    private DialogFragment getDialogFragment(Class<? extends DialogFragment> dialogClass) throws
            RuntimeException {
        try {
            return dialogClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "Couldn't create dialog for " + dialogClass.getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "IllegalAccessEception: trying to create " + dialogClass.getName(), e);
            throw new RuntimeException(e);
        }
    }

    protected enum DataAddEdit {
        ADD(DATA_ADD, DATA_ADD_TAG),
        EDIT(DATA_EDIT, DATA_EDIT_TAG);

        private int    mRequestCode;
        private String mTag;

        private DataAddEdit(int requestCode, String tag) {
            mRequestCode = requestCode;
            mTag = tag;
        }

        private int getRequestCode() {
            return mRequestCode;
        }

        private String getTag() {
            return mTag;
        }
    }
}
