package com.chdryra.android.reviewer.View.Interfaces;

import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface LaunchableConfig {
    String getTag();

    int getRequestCode();

    LaunchableUi getLaunchable(FactoryLaunchableUi launchableFactory);
}
