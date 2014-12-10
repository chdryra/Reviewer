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
 * @param <T>: {@link GvDataList.GvData} type.
 */
abstract class FragmentReviewGridAddEdit<T extends GvDataList.GvData> extends
        FragmentReviewGrid implements DialogReviewDataAddFragment
        .ReviewDataAddListener<T>, DialogReviewDataEditFragment.ReviewDataEditListener<T> {


    private final GvDataList.GvType mDataType;
    private final ActivityResultCode mDoDatumDelete = ActivityResultCode.DELETE;
    private final ActivityResultCode mDoDatumEdit   = ActivityResultCode.DONE;
    private final InputHandlerReviewData<T> mHandler;
    private ActivityResultCode mDoDatumAdd = ActivityResultCode.ADD;
    private ConfigReviewDataUI.ReviewDataUIConfig mAdderConfig;
    private ConfigReviewDataUI.ReviewDataUIConfig mEditorConfig;

    FragmentReviewGridAddEdit(GvDataList.GvType dataType) {
        this(dataType, new InputHandlerReviewData<T>(dataType));
    }

    FragmentReviewGridAddEdit(GvDataList.GvType dataType, InputHandlerReviewData<T>
            inputHandler) {
        mDataType = dataType;
        mHandler = inputHandler;
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

        setGridViewData(getController().getData(mDataType));
        setDeleteWhatTitle(mDataType.getDataString());
        setBannerButtonText(getResources().getString(R.string.add) + " " + mDataType
                .getDatumString());
    }

    @Override
    void onBannerButtonClick() {
        launchUi(mAdderConfig, null);
    }

    @Override
    void onGridItemClick(AdapterView<?> parent, View v, int position, long id) {
        launchUi(mEditorConfig, (T) parent.getItemAtPosition(position)); //TODO make type safe
    }

    @Override
    void setGridViewData(GvDataList gridData) {
        super.setGridViewData(gridData);
        mHandler.setData(gridData); //TODO make type safe
    }

    InputHandlerReviewData<T> getInputHandler() {
        return mHandler;
    }

    boolean doDatumAdd(T data) {
        boolean added = mHandler.add(data, getActivity());
        updateUI();
        return added;
    }

    void doDatumDelete(T data) {
        mHandler.delete(data);
        updateUI();
    }

    void doDatumEdit(T oldDatum, T newDatum) {
        mHandler.replace(oldDatum, newDatum, getActivity());
        updateUI();
    }

    void packGridCellData(T item, Bundle args) {
        mHandler.pack(InputHandlerReviewData.CurrentNewDatum.CURRENT, item, args);
    }

    final int getRequestCodeAdd() {
        return mAdderConfig.getRequestCode();
    }

    final int getRequestCodeEdit() {
        return mEditorConfig.getRequestCode();
    }

    void setAddResultCode(ActivityResultCode resultCode) {
        mDoDatumAdd = resultCode;
    }

    void onActivityAddRequested(int resultCode, Intent data) {
        if (data != null && ActivityResultCode.get(resultCode) == mDoDatumAdd) {
            doDatumAdd(mHandler.unpack(InputHandlerReviewData.CurrentNewDatum.NEW, data));
        }
    }

    void onActivityEditRequested(int resultCode, Intent data) {
        ActivityResultCode result = ActivityResultCode.get(resultCode);
        if (data != null && result == mDoDatumEdit) {
            T oldDatum = mHandler.unpack(InputHandlerReviewData.CurrentNewDatum.CURRENT, data);
            T newDatum = mHandler.unpack(InputHandlerReviewData.CurrentNewDatum.NEW, data);
            doDatumEdit(oldDatum, newDatum);
        } else if (data != null && result == mDoDatumDelete) {
            doDatumDelete(mHandler.unpack(InputHandlerReviewData.CurrentNewDatum.CURRENT, data));
        }
    }

    private void launchUi(ConfigReviewDataUI.ReviewDataUIConfig config, T dataToPack) {
        Bundle args = Administrator.get(getActivity()).pack(getController());

        if (dataToPack != null) packGridCellData(dataToPack, args);

        LauncherUI.launch(config.getReviewDataUI(), this, config.getRequestCode(),
                config.getTag(), args);
    }
}
