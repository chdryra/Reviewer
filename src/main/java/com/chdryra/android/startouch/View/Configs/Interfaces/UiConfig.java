/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.View.Configs.Interfaces;

import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface UiConfig {
    boolean hasConfig(String datumName);

    LaunchableConfig getViewer(String datumName);

    LaunchableConfig getEditor(String datumName);

    LaunchableConfig getAdder(String datumName);

    LaunchableConfig getBespokeViewer(String datumName);

    LaunchableConfig getBespokeDatumViewer(String datumName);

    LaunchableConfig getBespokeEditor(String datumName);

    LaunchableConfig getLogin();

    LaunchableConfig getProfile();

    LaunchableConfig getFeed();

    LaunchableConfig getPublish();

    LaunchableConfig getOptions();

    UiLauncher getUiLauncher();

    void setUiLauncher(UiLauncher launcher);
}
