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
public class FragmentReviewGridAddEdit<T extends GVReviewDataList.GVReviewData> extends
        FragmentReviewGrid<GVReviewDataList<T>> {
    public static final String GVTYPE = "com.chdryra.android.review.gvtype";
    protected GVType mDataType;
    private ActivityResultCode mDoDatumAdd    = ActivityResultCode.ADD;
    private ActivityResultCode mDoDatumDelete = ActivityResultCode.DELETE;
    private ActivityResultCode mDoDatumEdit   = ActivityResultCode.DONE;
    private InputHandlerReviewData<T>           mHandler;
    private ConfigReviewDataUI.ReviewDataConfig mDataOption;

    protected enum Action {ADD, DELETE, DONE}

    public FragmentReviewGridAddEdit() {
    }

    protected InputHandlerReviewData<T> getInputHandler() {
        return mHandler;
    }

    protected void doDatumAdd(Intent data) {
        mHandler.add(data, getActivity());
    }

    protected void doDatumDelete(Intent data) {
        mHandler.delete(data);
    }

    protected void doDatumEdit(Intent data) {
        mHandler.replace(data, getActivity());
    }

    protected Bundle packGridCellData(T item, Bundle args) {
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, item, args);
        return args;
    }

    protected void setResultCode(Action action, ActivityResultCode resultCode) {
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

    protected final int getRequestCodeAdd() {
        return mDataOption.getDialogAddRequestCode();
    }

    protected final int getRequestCodeEdit() {
        return mDataOption.getDialogEditRequestCode();
    }

    protected void onAddRequested(int resultCode, Intent data) {
        if (ActivityResultCode.get(resultCode) == mDoDatumAdd) doDatumAdd(data);
    }

    protected void onEditRequested(int resultCode, Intent data) {
        ActivityResultCode result = ActivityResultCode.get(resultCode);
        if (result == mDoDatumEdit) doDatumEdit(data);
        if (result == mDoDatumDelete) doDatumDelete(data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (requestCode == getRequestCodeAdd()) {
            onAddRequested(resultCode, data);
        } else if (requestCode == getRequestCodeEdit()) {
            onEditRequested(resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        updateUI();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mDataType == null) mDataType = (GVType) getArguments().getSerializable(GVTYPE);
        mHandler = new InputHandlerReviewData<T>(mDataType);
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
        DialogShower.show(mDataOption.getDialogAddFragment(), FragmentReviewGridAddEdit.this,
                getRequestCodeAdd(), mDataOption.getDialogAddDataTag(),
                Administrator.get(getActivity()).pack(getController()));
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        //TODO how to make this type safe
        @SuppressWarnings("unchecked") T item = (T) parent.getItemAtPosition(position);
        DialogShower.show(mDataOption.getDialogEditFragment(), FragmentReviewGridAddEdit.this,
                getRequestCodeEdit(), mDataOption.getDialogEditDataTag(),
                packGridCellData(item, Administrator.get(getActivity()).pack(getController())));
    }

    @Override
    void setGridViewData(GVReviewDataList<T> gridData) {
        super.setGridViewData(gridData);
        mHandler.setData(gridData);
    }
}
