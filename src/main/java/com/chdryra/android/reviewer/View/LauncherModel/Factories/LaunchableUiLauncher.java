/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 14 May, 2015
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.app.Activity;
import android.os.Bundle;

import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

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

    public void launch(LaunchableUi ui, Activity commissioner, int requestCode){
        launch(ui, commissioner, requestCode, new Bundle());
    }

    public void launch(LaunchableConfig config, Activity commissioner, Bundle args) {
        launch(config.getLaunchable(), commissioner, config.getRequestCode(), args);
    }
}
