package com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Plugins {
    public static ApplicationPlugins getPlugins(ApplicationLaunch.LaunchState state) {
        if(state.equals(ApplicationLaunch.LaunchState.RELEASE)) {
            return new ApplicationPluginsRelease();
        } else {
            return new ApplicationPluginsTest();
        }
    }
}
