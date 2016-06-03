/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Factories;

import android.os.Bundle;

import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 03/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UiLauncher {
    void launch(LaunchableUi ui, int requestCode, Bundle args);

    void launch(LaunchableUi ui, int requestCode);

    void launch(LaunchableConfig config, int requestCode, Bundle args);

    void launch(LaunchableConfig config, int requestCode);
}
