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
 * UI Fragment: base class for displaying review data. Generally the "Display" class in {@link
 * com.chdryra.android.reviewer.ConfigAddEditDisplay}.
 * <p/>
 * <p>
 * Banner button launches the "Add" class in {@link com.chdryra.android.reviewer
 * .ConfigAddEditDisplay} and a grid cell click will the "Edit" class. Long click on grid cell
 * usually does same as click.
 * </p>
 * <p>
 * User input handled by a {@link com.chdryra.android.reviewer.InputHandlerReviewData}.
 * </p>
 *
 * @param <T>: {@link com.chdryra.android.reviewer.GVReviewDataList.GVReviewData} type.
 */
abstract class FragmentReviewGridAddEdit<T extends GVReviewDataList.GVReviewData> extends
        FragmentReviewGrid<GVReviewDataList<T>> implements DialogReviewDataAddFragment
        .ReviewDataAddListener<T>, DialogReviewDataEditFragment.ReviewDataEditListener<T> {


    private final GVType                    mDataType;
    protected     InputHandlerReviewData<T> mHandler;
    private ActivityResultCode mDoDatumAdd    = ActivityResultCode.ADD;
    private ActivityResultCode mDoDatumDelete = ActivityResultCode.DELETE;
    private ActivityResultCode mDoDatumEdit   = ActivityResultCode.DONE;
    private ConfigReviewDataUI.ReviewDataUIConfig mAdderConfig;
    private ConfigReviewDataUI.ReviewDataUIConfig mEditorConfig;

    protected enum Action {ADD, DELETE, DONE}

    public FragmentReviewGridAddEdit(GVType dataType) {
        mDataType = dataType;
        mHandler = new InputHandlerReviewData<T>(mDataType);
    }

    protected InputHandlerReviewData<T> getInputHandler() {
        return mHandler;
    }

    @Override
    public boolean onReviewDataAdd(T data) {
        return doDatumAdd(data);
    }

    @Override
    public void onReviewDataDelete(T data) {
        doDatumDelete(data);
    }

    @Override
    public void onReviewDataEdit(T oldDatum, T newDatum) {
        doDatumEdit(oldDatum, newDatum);
    }

    protected boolean doDatumAdd(T data) {
        boolean added = mHandler.add(data, getActivity());
        updateUI();
        return added;
    }

    protected void doDatumDelete(T data) {
        mHandler.delete(data);
        updateUI();
    }

    protected void doDatumEdit(T oldDatum, T newDatum) {
        mHandler.replace(oldDatum, newDatum, getActivity());
        updateUI();
    }

    protected void packGridCellData(T item, Bundle args) {
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, item, args);
    }

    protected final int getRequestCodeAdd() {
        return mAdderConfig.getRequestCode();
    }

    protected final int getRequestCodeEdit() {
        return mEditorConfig.getRequestCode();
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
        if (data != null && ActivityResultCode.get(resultCode) == mDoDatumAdd) {
            T datum = getInputHandler().unpack(InputHandlerReviewData.CurrentNewDatum.NEW, data);
            doDatumAdd(datum);
        }
    }

    protected void onActivityEditRequested(int resultCode, Intent data) {
        ActivityResultCode result = ActivityResultCode.get(resultCode);
        if (data != null && result == mDoDatumEdit) {
            T oldDatum = getInputHandler().unpack(InputHandlerReviewData.CurrentNewDatum.CURRENT,
                    data);
            T newDatum = getInputHandler().unpack(InputHandlerReviewData.CurrentNewDatum.NEW,
                    data);
            doDatumEdit(oldDatum, newDatum);
        }
        if (data != null && result == mDoDatumDelete) {
            T datum = getInputHandler().unpack(InputHandlerReviewData.CurrentNewDatum.CURRENT,
                    data);
            doDatumDelete(datum);
        }
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

        ConfigReviewDataUI.Config config = ConfigReviewDataUI.getConfig(mDataType);
        mAdderConfig = config.getAdderConfig();
        mEditorConfig = config.getEditorConfig();

        //TODO how to make this type safe
        setGridViewData(getController().getData(mDataType));
        setDeleteWhatTitle(mDataType.getDataString());
        setBannerButtonText(getResources().getString(R.string.add) + " " + mDataType.getDatumString
                ());
    }

    @Override
    protected void onBannerButtonClick() {
        Bundle args = Administrator.get(getActivity()).pack(getController());

        LauncherUI.launch(mAdderConfig.getReviewDataUI(), this,
                mAdderConfig.getRequestCode(), mAdderConfig.getTag(), args);
    }

    @Override
    protected void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        Bundle args = Administrator.get(getActivity()).pack(getController());

        //TODO make type safe
        packGridCellData((T) parent.getItemAtPosition(position), args);

        LauncherUI.launch(mEditorConfig.getReviewDataUI(), this,
                mEditorConfig.getRequestCode(), mEditorConfig.getTag(), args);
    }

    @Override
    void setGridViewData(GVReviewDataList<T> gridData) {
        super.setGridViewData(gridData);
        mHandler.setData(gridData);
    }
}
