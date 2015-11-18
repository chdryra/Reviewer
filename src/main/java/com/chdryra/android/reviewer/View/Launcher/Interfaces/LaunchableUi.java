/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 October, 2014
 */

package com.chdryra.android.reviewer.View.Launcher.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 23/10/2014
 * Email: rizwan.choudrey@gmail.com
 */

import com.chdryra.android.reviewer.View.Launcher.Implementation.LauncherUiImpl;

/**
 * For UIs that can be launched by a {@link LauncherUiImpl}.
 * Usually by calling "launcher.launch(this)".
 */
public interface LaunchableUi {
    String getLaunchTag();

    void launch(LauncherUi launcher);
}
