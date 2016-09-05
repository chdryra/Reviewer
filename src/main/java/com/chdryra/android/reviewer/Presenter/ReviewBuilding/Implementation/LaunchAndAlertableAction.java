/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
class LaunchAndAlertableAction<T extends GvData> extends ReviewDataEditorActionBasic<T>
        implements AlertListener {
    private final String mLaunchTag;
    private final LaunchableConfig mConfig;

    private int mLaunchableRequestCode = -1;
    private int mAlertDialogRequestCode;

    LaunchAndAlertableAction(String launchTag, LaunchableConfig config) {
        mLaunchTag = launchTag;
        mConfig = config;
    }

    public int getLaunchableRequestCode() {
        return mLaunchableRequestCode;
    }

    void setLaunchableRequestCode(String tag) {
        mLaunchableRequestCode = RequestCodeGenerator.getCode(mLaunchTag + tag);
    }

    void launchDefaultConfig(Bundle args){
        launch(mConfig, args);
    }

    void launch(LaunchableConfig config, Bundle args) {
        setLaunchableRequestCode(config.getTag());
        getApp().getUiLauncher().launch(config, getLaunchableRequestCode(), args);
    }

    public int getAlertRequestCode() {
        return mAlertDialogRequestCode;
    }

    void doAlertPositive(Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mAlertDialogRequestCode) doAlertPositive(args);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    void showAlert(String alert, int requestCode, Bundle args) {
        mAlertDialogRequestCode = requestCode;
        getApp().getCurrentScreen().showAlert(alert, requestCode, args);
    }
}
