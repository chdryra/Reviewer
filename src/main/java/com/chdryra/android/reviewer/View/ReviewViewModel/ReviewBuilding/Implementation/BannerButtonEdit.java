package com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.ActivityResultCode;
import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Dialogs.DialogGvDataAdd;
import com.chdryra.android.reviewer.View.Dialogs.DialogShower;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataPacker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.BannerButtonAction;

/**
 * Created by: Rizwan Choudrey
 * On: 09/10/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonEdit<T extends GvData> extends ReviewDataEditorActionBasic<T>
        implements
        BannerButtonAction<T>,
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataAdd.GvDataAddListener<T>,
        ActivityResultListener {

    private String mTitle;
    private GvDataType<T> mDataType;
    private FactoryGvData mDataFactory;
    private GvDataPacker<T> mDataPacker;
    private final LaunchableConfig<T> mConfig;
    private GvDataList<T> mAdded;
    private FactoryLaunchableUi mLaunchableFactory;

    protected BannerButtonEdit(String title,
                               GvDataType<T> dataType,
                               FactoryGvData dataFactory,
                               GvDataPacker<T> dataPacker,
                               LaunchableConfig<T> adderConfig,
                               FactoryLaunchableUi launchableFactory) {
        mTitle = title;
        mDataType = dataType;
        mDataFactory = dataFactory;
        mDataPacker = dataPacker;
        mConfig = adderConfig;
        mLaunchableFactory = launchableFactory;
        initDataList();
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

    protected void launchConfig(LaunchableConfig<? extends T> config) {
        LaunchableUi ui = config.getLaunchable(mLaunchableFactory);
        mLaunchableFactory.launch(ui, getActivity(), config.getRequestCode(), config.getTag());
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
