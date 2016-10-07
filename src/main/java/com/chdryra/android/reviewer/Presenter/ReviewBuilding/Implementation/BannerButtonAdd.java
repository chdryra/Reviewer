/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataAddListener;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 09/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BannerButtonAdd<T extends GvDataParcelable> extends LaunchAndAlertableAction<T> implements
        BannerButtonAction<T>, DataAddListener<T>, ActivityResultListener {
    private static final String TAG = "BannerButtonAdd:";

    private final String mTitle;
    private final ParcelablePacker<T> mDataPacker;

    private final GvDataList<T> mAdded;

    public BannerButtonAdd(LaunchableConfig adderConfig,
                           String title,
                           GvDataList<T> emptyListToAddTo,
                           ParcelablePacker<T> dataPacker) {
        super(TAG, adderConfig);
        mTitle = title;
        mAdded = emptyListToAddTo;
        mDataPacker = dataPacker;
        initDataList();
    }

    boolean addData(T data) {
        return getEditor().add(data);
    }

    @Override
    public void onClick(View v) {
        launchDefaultConfig(new Bundle());
    }

    @Override
    public boolean onLongClick(View v) {
        onClick(v);
        return true;
    }

    @Override
    public void setButton(BannerButton button) {
        button.setTitle(mTitle);
    }

    @Override
    public boolean onAdd(T data, int requestCode) {
        if(data == null) return false;

        boolean success = false;
        if (requestCode == getLaunchableRequestCode()) {
            success = addData(data);
            if (success) mAdded.add(data);
        }
        return success;
    }

    @Override
    public void onCancel(int requestCode) {
        if (requestCode == getLaunchableRequestCode()) {
            for (T added : mAdded) {
                getEditor().delete(added);
            }
            initDataList();
        }
    }

    @Override
    public void onDone(int requestCode) {
        if (requestCode == getLaunchableRequestCode()) initDataList();
    }

    //For launchable activities
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getLaunchableRequestCode()
                && ActivityResultCode.get(resultCode) == ActivityResultCode.DONE) {
            T datum = mDataPacker.unpack(ParcelablePacker.CurrentNewDatum.NEW, data);
            if(datum != null) onAdd(datum, requestCode);
        }
    }

    private void initDataList() {
        mAdded.clear();
    }
}
