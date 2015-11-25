package com.chdryra.android.reviewer.View.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ConfigDataUi {
    LaunchableConfig getViewerConfig(String datumName);

    LaunchableConfig getEditorConfig(String datumName);

    LaunchableConfig getAdderConfig(String datumName);

    LaunchableConfig getBuildReviewConfig();

    LaunchableConfig getMapEditorConfig();
}
