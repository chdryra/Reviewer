/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs;

import android.util.Log;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.LauncherUiImpl;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfigAlertable;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;

/**
 * Encapsulates a configuration for a UI that can add, edit, view review data of a certain
 * {@link GvDataType}. Packages together:
 * <ul>
 * <li>A {@link LaunchableUi} implementation for
 * adding/editing review data of a certain type</li>
 * <li>An integer request code (required when one activity launches another)</li>
 * <li>A String tag that may be used (if ultimately launching a dialog)</li>
 * </ul>
 * The {@link LaunchableUi} is launched using a
 * {@link LauncherUiImpl}
 */
public class LaunchableConfigAlertableImpl extends LaunchableConfigImpl implements LaunchableConfigAlertable {
    private static final String TAG = "LaunchableConfigImpl";
    private final Class<? extends LaunchableUiAlertable> mUiClass;

    public LaunchableConfigAlertableImpl(Class<? extends LaunchableUiAlertable> uiClass,
                                         int requestCode, String tag) {
        super(uiClass, requestCode, tag);
        mUiClass = uiClass;
    }

    @Override
    public LaunchableUiAlertable getLaunchable() throws RuntimeException {
        if (mUiClass == null) return null;
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
}
