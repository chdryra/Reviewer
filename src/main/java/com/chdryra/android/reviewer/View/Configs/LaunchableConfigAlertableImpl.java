/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs;

import android.util.Log;

import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfigAlertable;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;

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
