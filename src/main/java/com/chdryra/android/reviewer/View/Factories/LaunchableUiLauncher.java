/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.View.Factories;

import android.app.Activity;
import android.os.Bundle;

import com.chdryra.android.reviewer.View.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 14/05/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchableUiLauncher {
    private FactoryLauncherUi mLauncherFactory;

    public LaunchableUiLauncher(FactoryLauncherUi launcherFactory) {
        mLauncherFactory = launcherFactory;
    }

    public void launch(LaunchableUi ui, Activity commissioner, int requestCode, Bundle args) {
        ui.launch(mLauncherFactory.newLauncher(commissioner, requestCode, ui.getLaunchTag(), args));
    }

    public void launch(LaunchableUi ui, Activity commissioner, int requestCode, String tag){
        ui.launch(mLauncherFactory.newLauncher(commissioner, requestCode, tag));
    }

    public void launch(LaunchableConfig config, Activity commissioner, Bundle args) {
        launch(config.getLaunchable(this), commissioner, config.getRequestCode(), args);
    }
}
