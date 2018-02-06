/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.View.Configs.Implementation;

import android.util.Log;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Encapsulates a configuration for a UI that can add, edit, view review data of a certain
 * {@link GvDataType}. Packages together:
 * <ul>
 * <li>A {@link LaunchableUi} implementation for
 * adding/editing review data of a certain type</li>
 * <li>An integer request code (required when one activity launches another)</li>
 * <li>A String tag that may be used (if ultimately launching a dialog)</li>
 * </ul>
 */
public class LaunchableConfigImpl implements LaunchableConfig {
    private static final String TAG = "LaunchableConfigImpl";
    private final Class<? extends LaunchableUi> mUiClass;
    private final int mRequestCode;

    private UiLauncher mLauncher;

    public LaunchableConfigImpl(Class<? extends LaunchableUi> UiClass, int requestCode) {
        mUiClass = UiClass;
        mRequestCode = requestCode;
    }

    private LaunchableUi newLaunchable() {
        try {
            return mUiClass.newInstance();
        } catch (InstantiationException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "Couldn't create UI for " + mUiClass.getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //If this happens not good so throwing runtime exception
            Log.e(TAG, "IllegalAccessException: trying to create " + mUiClass.getName(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getDefaultRequestCode() {
        return mRequestCode;
    }

    @Override
    public void setLauncher(UiLauncher launcher) {
        mLauncher = launcher;
    }

    @Override
    public void launch() {
        launch(new UiLauncherArgs(getDefaultRequestCode()));
    }

    @Override
    public void launch(UiLauncherArgs args) {
        if(mLauncher != null) mLauncher.launch(newLaunchable(), args);
    }
}
