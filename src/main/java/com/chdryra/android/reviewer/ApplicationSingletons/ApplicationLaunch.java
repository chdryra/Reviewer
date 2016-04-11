/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Factories.FactoryApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationPlugins.Plugins;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationLaunch {
    private static ApplicationLaunch sLaunch;

    public enum LaunchState {RELEASE, TEST}

    private ApplicationLaunch(Context context, LaunchState launchState) {
        ApplicationInstance.newInstance(context, newApplicationContext(context, launchState));
    }

    private ApplicationContext newApplicationContext(Context context, LaunchState launchState) {
        FactoryApplicationContext factory = new FactoryApplicationContext();
        return factory.newReleaseContext(context, Plugins.getPlugins(context, launchState));
    }

    public static void intitialiseLaunchIfNecessary(Context context, LaunchState launchState) {
        if(sLaunch == null) sLaunch = new ApplicationLaunch(context, launchState);
    }
}
