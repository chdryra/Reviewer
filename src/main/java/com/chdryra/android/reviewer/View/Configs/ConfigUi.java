/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs;

import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ConfigUi {
    LaunchableConfig getViewer(String datumName);

    LaunchableConfig getEditor(String datumName);

    LaunchableConfig getAdder(String datumName);

    LaunchableConfig getLogin();

    LaunchableConfig getSignUp();

    LaunchableConfig getUsersFeed();

    LaunchableConfig getBuildReview();

    LaunchableConfig getMapEditor();

    LaunchableConfig getShareReview();

    LaunchableConfig getReviewOptions();

    UiLauncher getUiLauncher();

    void setUiLauncher(UiLauncher launcher);
}
