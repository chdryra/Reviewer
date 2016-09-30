/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Interfaces;

import android.os.Bundle;

import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface LaunchableConfig {
    String getTag();

    int getRequestCode();

    LaunchableUi getLaunchable();

    LaunchableConfig setLauncher(UiLauncher launcher);

    UiLauncher getLauncher();

    void launch(Bundle args);

    void launch(int requestCode, Bundle args);
}
