/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class Plugins {
    public static ApplicationPlugins getPlugins(Context context, ApplicationLaunch.LaunchState state) {
        if(state.equals(ApplicationLaunch.LaunchState.RELEASE)) {
            return new ApplicationPluginsRelease(context);
        } else {
            return new ApplicationPluginsTest(context);
        }
    }
}
