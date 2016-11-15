/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs.Interfaces;

import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface UiConfig {
    LaunchableConfig getViewer(String datumName);

    LaunchableConfig getEditor(String datumName);

    LaunchableConfig getAdder(String datumName);

    LaunchableConfig getLogin();

    LaunchableConfig getSignUp();

    LaunchableConfig getFeed();

    LaunchableConfig getBuildReview();

    LaunchableConfig getFormattedReview();

    LaunchableConfig getMapEditor();

    LaunchableConfig getNodeMapper();

    LaunchableConfig getPublish();

    LaunchableConfig getReviewOptions();

    UiLauncher getUiLauncher();

    void setUiLauncher(UiLauncher launcher);
}
