/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import android.os.Bundle;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.Dialogs.DialogShower;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 03/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchAndAlertableAction<T extends GvData> extends ReviewDataEditorActionBasic<T>
        implements AlertListener {
    private final String mLaunchTag;
    private final LaunchableConfig mConfig;
    private final LaunchableUiLauncher mLauncher;

    private int mLaunchableRequestCode = -1;
    private int mAlertDialogRequestCode;

    public LaunchAndAlertableAction(String launchTag, LaunchableConfig config, LaunchableUiLauncher
            launcher) {
        mLaunchTag = launchTag;
        mConfig = config;
        mLauncher = launcher;
    }

    public int getLaunchableRequestCode() {
        return mLaunchableRequestCode;
    }

    protected void setLaunchableRequestCode(String tag) {
        mLaunchableRequestCode = RequestCodeGenerator.getCode(mLaunchTag + tag);
    }

    protected void launchDefaultConfig(Bundle args){
        launch(mConfig, args);
    }

    protected void launch(LaunchableConfig config, Bundle args) {
        setLaunchableRequestCode(config.getTag());
        mLauncher.launch(config, getActivity(), getLaunchableRequestCode(), args);
    }

    public int getAlertRequestCode() {
        return mAlertDialogRequestCode;
    }

    protected void doAlertNegative(Bundle args) {

    }

    protected void doAlertPositive(Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        if (requestCode == mAlertDialogRequestCode) doAlertPositive(args);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        if (requestCode == mAlertDialogRequestCode) doAlertNegative(args);
    }

    protected void showAlertDialog(String alert, int requestCode, Bundle args) {
        mAlertDialogRequestCode = requestCode;
        DialogShower.showAlert(alert, getActivity(), requestCode, args);
    }
}
