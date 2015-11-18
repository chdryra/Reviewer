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
import com.chdryra.android.reviewer.View.Launcher.FactoryLaunchable;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.BannerButtonActionNone;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.View.ReviewViewModel.ReviewBuilding.Interfaces.ReviewDataEditor;

/**
 * Created by: Rizwan Choudrey
 * On: 09/10/2015
 * Email: rizwan.choudrey@gmail.com
 */ //Classes
public class BannerButtonEdit<T extends GvData> extends BannerButtonActionNone
        implements
        DialogAlertFragment.DialogAlertListener,
        DialogGvDataAdd.GvDataAddListener<T>,
        ActivityResultListener {

    private GvDataType<T> mDataType;
    private final LaunchableConfig mConfig;
    private GvDataList<T> mAdded;
    private ReviewDataEditor<T> mEditor;

    protected BannerButtonEdit(GvDataType<T> dataType, String title,
                               LaunchableConfig<T> adderConfig, FactoryLaunchable launchableFactory) {
        super(title);
        mDataType = dataType;
        mConfig = adderConfig;
        initDataList();
    }

    protected int getLaunchableRequestCode() {
        return mConfig.getRequestCode();
    }

    protected boolean addData(T data) {
        return mEditor.add(data);
    }

    protected void showAlertDialog(String alert, int requestCode) {
        DialogShower.showAlert(alert, getActivity(), requestCode, DialogAlertFragment.ALERT_TAG);
    }

    private void initDataList() {
        mAdded = FactoryGvData.newDataList(mDataType);
    }

    //Overridden
    @Override
    public void onClick(View v) {
        LauncherUi.launch(mConfig.getLaunchable(), getActivity(), getLaunchableRequestCode(),
                mConfig.getTag(), new Bundle());
    }

    @Override
    public void onAttachReviewView() {
        try {
            mEditor = (ReviewDataEditor<T>)getReviewView();
        } catch (ClassCastException e) {
            throw new RuntimeException(e);
        }
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
                mEditor.delete(added);
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
            T datum = (T) GvDataPacker.unpackItem(GvDataPacker.CurrentNewDatum.NEW, data);
            onGvDataAdd(datum, requestCode);
        }
    }
}
