/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.reviewer.GVReviewDataList.GVType;

/**
 * UI Fragment: base class for adding/editing review data UIs.
 * <p/>
 * <p>
 * By default assumes the banner button will launch an Add dialog and a grid cell click will
 * launch an edit dialog (as defined in OptionsReviewBuild). If using this default then need to
 * implement <code>packGridCellData(.)</code> to bundle the grid cell selected data for editing
 * so that it can be passed to the edit dialog.
 * </p>
 * <p>
 * Need to implement <code>getDataType()</code> for proper data initialisation.
 * </p>
 * <p>
 * Need to implement <code>addData(.)</code> and <code>editData(.)</code> to know what to do
 * once the appropriate dialog or activity has returned the user input.
 * </p>
 *
 * @param <T>: GVData type shown in grid cell
 */
abstract class FragmentReviewGridAddEdit<T extends GVReviewDataList.GVReviewData> extends
        FragmentReviewGrid<GVReviewDataList<T>>
        implements DialogReviewDataAddFragment.DialogReviewDataAddListener,
        DialogReviewDataEditFragment.DialogReviewDataEditListener {

    public static final String GVTYPE = "com.chdryra.android.review.gvtype";
    protected InputHandlerReviewData<T> mHandler;
    private   GVType                    mDataType;
    private ActivityResultCode mDoDatumAdd    = ActivityResultCode.ADD;
    private ActivityResultCode mDoDatumDelete = ActivityResultCode.DELETE;
    private ActivityResultCode mDoDatumEdit   = ActivityResultCode.DONE;
    private ConfigReviewDataUI.ReviewDataConfig mDataOption;

    protected enum Action {ADD, DELETE, DONE}

    public FragmentReviewGridAddEdit(GVType dataType) {
        mDataType = dataType;
        mHandler = new InputHandlerReviewData<T>(mDataType);
    }

    protected InputHandlerReviewData<T> getInputHandler() {
        return mHandler;
    }

    @Override
    public boolean onDialogAddClick(Intent data) {
        return doDatumAdd(data);
    }

    @Override
    public void onDialogDeleteClick(Intent data) {
        doDatumDelete(data);
    }

    @Override
    public void onDialogDoneClick(Intent data) {
        doDatumEdit(data);
    }

    protected boolean doDatumAdd(Intent data) {
        boolean added = mHandler.add(data, getActivity());
        updateUI();
        return added;
    }

    protected void doDatumDelete(Intent data) {
        mHandler.delete(data);
        updateUI();
    }

    protected boolean doDatumEdit(Intent data) {
        boolean replaced = mHandler.replace(data, getActivity());
        updateUI();
        return replaced;
    }

    protected Bundle packGridCellData(T item, Bundle args) {
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, item, args);
        return args;
    }

    protected final int getRequestCodeAdd() {
        return mDataOption.getDialogAddConfig().getRequestCode();
    }

    protected final int getRequestCodeEdit() {
        return mDataOption.getDialogEditConfig().getRequestCode();
    }

    protected void setActivityResultCode(Action action, ActivityResultCode resultCode) {
        switch (action) {
            case ADD:
                mDoDatumAdd = resultCode;
                break;
            case DELETE:
                mDoDatumDelete = resultCode;
                break;
            case DONE:
                mDoDatumEdit = resultCode;
                break;
        }
    }

    protected void onActivityAddRequested(int resultCode, Intent data) {
        if (data != null && ActivityResultCode.get(resultCode) == mDoDatumAdd) doDatumAdd(data);
    }

    protected void onActivityEditRequested(int resultCode, Intent data) {
        ActivityResultCode result = ActivityResultCode.get(resultCode);
        if (data != null && result == mDoDatumEdit) doDatumEdit(data);
        if (data != null && result == mDoDatumDelete) doDatumDelete(data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (data == null) return;

        if (requestCode == getRequestCodeAdd()) {
            onActivityAddRequested(resultCode, data);
        } else if (requestCode == getRequestCodeEdit()) {
            onActivityEditRequested(resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (mDataType == null) mDataType = (GVType) getArguments().getSerializable(GVTYPE);
//        mHandler = new InputHandlerReviewData<T>(mDataType);
        mDataOption = ConfigReviewDataUI.get(mDataType);

        //TODO how to make this type safe
        @SuppressWarnings("unchecked") GVReviewDataList<T> data = getController().getData
                (mDataType);
        setGridViewData(data);
        setDeleteWhatTitle(mDataType.getDataString());
        setBannerButtonText(getResources().getString(R.string.add) + " " + mDataType.getDatumString
                ());
    }

    @Override
    protected void onBannerButtonClick() {
        DialogShower.show(mDataOption.getDialogAddConfig().getDialogFragment(),
                FragmentReviewGridAddEdit.this,
                getRequestCodeAdd(), mDataOption.getDialogAddConfig().getTag(),
                Administrator.get(getActivity()).pack(getController()));
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        //TODO how to make this type safe
        @SuppressWarnings("unchecked") T item = (T) parent.getItemAtPosition(position);
        DialogShower.show(mDataOption.getDialogEditConfig().getDialogFragment(),
                FragmentReviewGridAddEdit.this,
                getRequestCodeEdit(), mDataOption.getDialogEditConfig().getTag(),
                packGridCellData(item, Administrator.get(getActivity()).pack(getController())));
    }

    @Override
    void setGridViewData(GVReviewDataList<T> gridData) {
        super.setGridViewData(gridData);
        mHandler.setData(gridData);
    }
}
