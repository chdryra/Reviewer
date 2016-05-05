/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs;

import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfigAlertable;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ConfigUi {
    LaunchableConfig getViewerConfig(String datumName);

    LaunchableConfig getEditorConfig(String datumName);

    LaunchableConfig getAdderConfig(String datumName);

    LaunchableConfig getSignUpConfig();

    LaunchableConfig getFeedConfig();

    LaunchableConfig getBuildReviewConfig();

    LaunchableConfig getMapEditorConfig();

    LaunchableConfig getShareReviewConfig();

    LaunchableConfigAlertable getShareEditConfig();
}
