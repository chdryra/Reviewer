/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationContexts.Factories.FactoryApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPlugins;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsRelease;
import com.chdryra.android.reviewer.ApplicationPlugins.ApplicationPluginsTest;

/**
 * Created by: Rizwan Choudrey
 * On: 05/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationLaunch {
    private static boolean sLaunched = false;

    public enum LaunchState {RELEASE, TEST}

    public static void launchIfNecessary(Context context, LaunchState launchState) {
        if(!sLaunched) {
            ApplicationPlugins plugins = getPlugins(context, launchState);

            FactoryApplicationContext factory = new FactoryApplicationContext();
            ApplicationContext appContext = factory.newReleaseContext(context, plugins);

            ApplicationInstance.newInstance(context, appContext);

            sLaunched = true;
        }
    }

    @NonNull
    private static ApplicationPlugins getPlugins(Context context, LaunchState launchState) {
        ApplicationPlugins plugins;
        if(launchState.equals(LaunchState.RELEASE)) {
            plugins = new ApplicationPluginsRelease(context);
        } else {
            plugins = new ApplicationPluginsTest(context);
        }
        return plugins;
    }
}
