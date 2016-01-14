package com.chdryra.android.reviewer.View.Configs;

import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ConfigUi {
    LaunchableConfig getViewerConfig(String datumName);

    LaunchableConfig getEditorConfig(String datumName);

    LaunchableConfig getAdderConfig(String datumName);

    LaunchableConfig getBuildReviewConfig();

    LaunchableConfig getMapEditorConfig();

    LaunchableConfig getShareReviewConfig();
}
