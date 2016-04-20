/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.app.Fragment;

import com.chdryra.android.mygenerallibrary.Activities.ActivitySingleFragment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentEditLocationMap;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments.FragmentLogin;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;


/**
 * UI Activity holding {@link FragmentEditLocationMap}: mapping and editing a location.
 */
public class ActivityLogin extends ActivitySingleFragment implements LaunchableUi {
    private static final String TAG = "ActivityLogin";
    private static final String KEY = "ActivityLogin";

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), KEY);
    }

    @Override
    protected Fragment createFragment() {
        ApplicationLaunch.launchIfNecessary(this, ApplicationLaunch.LaunchState.TEST);

        return FragmentLogin.newInstance();
    }
}
