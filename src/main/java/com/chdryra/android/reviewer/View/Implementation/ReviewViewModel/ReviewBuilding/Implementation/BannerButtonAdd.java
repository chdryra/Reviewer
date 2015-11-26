package com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.ReviewBuilding.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Utils.DialogShower;
import com.chdryra.android.reviewer.View.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.Implementation.Dialogs.Implementation.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Interfaces.BannerButtonAction;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 09/10/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonAdd<T extends GvData> extends ReviewDataEditorActionBasic<T>
        implements
        BannerButtonAction<T>,
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataAdd.GvDataAddListener<T>,
        ActivityResultListener {

    private final String mTitle;
    private final GvDataType<T> mDataType;
    private final FactoryGvData mDataFactory;
    private final GvDataPacker<T> mDataPacker;
    private final LaunchableConfig mConfig;
    private final LaunchableUiLauncher mLaunchableFactory;
    private GvDataList<T> mAdded;

    public BannerButtonAdd(LaunchableConfig adderConfig,
                           String title,
                           GvDataType<T> dataType,
                           FactoryGvData dataFactory,
                           GvDataPacker<T> dataPacker,
                           LaunchableUiLauncher launchableFactory) {
        mTitle = title;
        mDataType = dataType;
        mDataFactory = dataFactory;
        mDataPacker = dataPacker;
        mConfig = adderConfig;
        mLaunchableFactory = launchableFactory;
        initDataList();
    }

    public LaunchableUiLauncher getLaunchableFactory() {
        return mLaunchableFactory;
    }

    protected int getLaunchableRequestCode() {
        return mConfig.getRequestCode();
    }

    protected boolean addData(T data) {
        return getEditor().add(data);
    }

    protected void showAlertDialog(String alert, int requestCode) {
        DialogShower.showAlert(alert, getActivity(), requestCode, DialogAlertFragment.ALERT_TAG);
    }

    private void initDataList() {
        mAdded = mDataFactory.newDataList(mDataType);
    }

    protected void launchConfig(LaunchableConfig config) {
        mLaunchableFactory.launch(config, getActivity(), new Bundle());
    }

    //Overridden
    @Override
    public void onClick(View v) {
        launchConfig(mConfig);
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public String getButtonTitle() {
        return mTitle;
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {

    }

    @Override
    public boolean onGvDataAdd(T data, int requestCode) {
        boolean success = false;
        if(requestCode == getLaunchableRequestCode()) {
            success = addData(data);
            if (success) mAdded.add(data);
        }
        return success;
    }

    @Override
    public void onGvDataCancel(int requestCode) {
        if(requestCode == getLaunchableRequestCode()) {
            for (T added : mAdded) {
                getEditor().delete(added);
            }
            initDataList();
        }
    }

    @Override
    public void onGvDataDone(int requestCode) {
        if(requestCode == getLaunchableRequestCode()) initDataList();
    }

    //For location and URL add activities
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getLaunchableRequestCode() && data != null
                && ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
            T datum = mDataPacker.unpack(GvDataPacker.CurrentNewDatum.NEW, data);
            onGvDataAdd(datum, requestCode);
        }
    }
}
