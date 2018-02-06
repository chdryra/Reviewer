/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchAndAlertableAction<T extends GvData> extends ReviewDataEditorActionBasic<T>
        implements AlertListener {
    private final String mLaunchTag;
    private final LaunchableConfig mConfig;

    private int mLaunchableRequestCode = -1;
    private int mAlertDialogRequestCode;

    public LaunchAndAlertableAction(String launchTag, LaunchableConfig config) {
        mLaunchTag = launchTag;
        mConfig = config;
    }

    protected int getLaunchableRequestCode() {
        return mLaunchableRequestCode;
    }

    protected void setLaunchableRequestCode(int defaultCode) {
        mLaunchableRequestCode = RequestCodeGenerator.getCode(mLaunchTag + String.valueOf(defaultCode));
    }

    protected void launchDefaultConfig(Bundle args){
        launch(mConfig, args);
    }

    protected void launch(LaunchableConfig config, Bundle args) {
        setLaunchableRequestCode(config.getDefaultRequestCode());
        config.launch(new UiLauncherArgs(getLaunchableRequestCode()).setBundle(args));
    }

    protected void doAlertPositive(Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mAlertDialogRequestCode) doAlertPositive(args);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    protected void showAlert(String alert, int requestCode, Bundle args) {
        mAlertDialogRequestCode = requestCode;
        getCurrentScreen().showAlert(alert, requestCode, this, args);
    }
}
