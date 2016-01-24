/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs;

import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit or display the data.
 * <p/>
 * <p>
 * Retrieves relevant add, edit and display UIs for each {@link com.chdryra.android.reviewer
 * .GVReviewDataList.GVType} from {@link UiAndroid.DefaultLaunchables}
 * and packages them with request codes and tags so that they can be appropriately launched
 * by whichever UI needs them in response to a user interaction.
 * </p>
 */
public final class ConfigUiImpl implements ConfigUi {
    private final Map<String, LaunchableConfigsHolder<? extends GvData>> mConfigsMap;
    private LaunchableConfig mBuildReviewConfig;
    private LaunchableConfig mEditOnMapConfig;
    private LaunchableConfig mShareReviewConfig;

    public ConfigUiImpl(Iterable<? extends LaunchableConfigsHolder<?>> configs,
                        LaunchableConfig buildReviewConfig,
                        LaunchableConfig editOnMapConfig,
                        LaunchableConfig shareReviewConfig) {
        mConfigsMap = new HashMap<>();
        for (LaunchableConfigsHolder<?> config : configs) {
            mConfigsMap.put(config.getGvDataType().getDatumName(), config);
        }
        mBuildReviewConfig = buildReviewConfig;
        mEditOnMapConfig = editOnMapConfig;
        mShareReviewConfig = shareReviewConfig;
    }

    @Override
    public LaunchableConfig getViewerConfig(String datumName) {
        return getConfigs(datumName).getViewerConfig();
    }

    @Override
    public LaunchableConfig getEditorConfig(String datumName) {
        return getConfigs(datumName).getEditorConfig();
    }

    @Override
    public LaunchableConfig getAdderConfig(String datumName) {
        return getConfigs(datumName).getAdderConfig();
    }

    @Override
    public LaunchableConfig getBuildReviewConfig() {
        return mBuildReviewConfig;
    }

    @Override
    public LaunchableConfig getMapEditorConfig() {
        return mEditOnMapConfig;
    }

    @Override
    public LaunchableConfig getShareReviewConfig() {
        return mShareReviewConfig;
    }

    private LaunchableConfigsHolder<?> getConfigs(String datumName) {
        return mConfigsMap.get(datumName);
    }
}
