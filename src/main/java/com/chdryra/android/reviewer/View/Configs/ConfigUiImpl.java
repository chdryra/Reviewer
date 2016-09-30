/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit or display the data.
 * <p/>
 * <p>
 * Retrieves relevant add, edit and display UIs for each {@link com.chdryra.android.reviewer
 * .GVReviewDataList.GVType} from {@link com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid.AndroidLaunchables}
 * and packages them with request codes and tags so that they can be appropriately launched
 * by whichever UI needs them in response to a user interaction.
 * </p>
 */
public final class ConfigUiImpl implements ConfigUi {
    private final Map<String, LaunchableConfigsHolder<? extends GvData>> mConfigsMap;
    private final LaunchableConfig mLoginConfig;
    private final LaunchableConfig mSignUpConfig;
    private final LaunchableConfig mUsersFeedConfig;
    private final LaunchableConfig mBuildReviewConfig;
    private final LaunchableConfig mEditOnMapConfig;
    private final LaunchableConfig mShareReviewConfig;
    private final LaunchableConfig mShareEditConfig;

    private UiLauncher mLauncher;

    public ConfigUiImpl(Iterable<? extends LaunchableConfigsHolder<?>> dataConfigs,
                        LaunchableConfig loginConfig,
                        LaunchableConfig signUpConfig,
                        LaunchableConfig usersFeedConfig,
                        LaunchableConfig buildReviewConfig,
                        LaunchableConfig editOnMapConfig,
                        LaunchableConfig shareReviewConfig,
                        LaunchableConfig shareEditConfig) {
        mConfigsMap = new HashMap<>();
        for (LaunchableConfigsHolder<?> dataConfig : dataConfigs) {
            mConfigsMap.put(dataConfig.getGvDataType().getDatumName(), dataConfig);
        }
        mLoginConfig = loginConfig;
        mSignUpConfig = signUpConfig;
        mUsersFeedConfig = usersFeedConfig;
        mBuildReviewConfig = buildReviewConfig;
        mEditOnMapConfig = editOnMapConfig;
        mShareReviewConfig = shareReviewConfig;
        mShareEditConfig = shareEditConfig;
    }

    @Override
    public LaunchableConfig getViewer(String datumName) {
        return getConfigs(datumName).getViewerConfig().setLauncher(mLauncher);
    }

    @Override
    public LaunchableConfig getEditor(String datumName) {
        return getConfigs(datumName).getEditorConfig().setLauncher(mLauncher);
    }

    @Override
    public LaunchableConfig getAdder(String datumName) {
        return getConfigs(datumName).getAdderConfig().setLauncher(mLauncher);
    }

    @Override
    public LaunchableConfig getLogin() {
        return mLoginConfig.setLauncher(mLauncher);
    }

    @Override
    public LaunchableConfig getSignUp() {
        return mSignUpConfig.setLauncher(mLauncher);
    }

    @Override
    public LaunchableConfig getUsersFeed() {
        return mUsersFeedConfig.setLauncher(mLauncher);
    }

    @Override
    public LaunchableConfig getBuildReview() {
        return mBuildReviewConfig.setLauncher(mLauncher);
    }

    @Override
    public LaunchableConfig getMapEditor() {
        return mEditOnMapConfig.setLauncher(mLauncher);
    }

    @Override
    public LaunchableConfig getShareReview() {
        return mShareReviewConfig.setLauncher(mLauncher);
    }

    @Override
    public LaunchableConfig getReviewOptions() {
        return mShareEditConfig.setLauncher(mLauncher);
    }

    @Override
    public UiLauncher getUiLauncher() {
        return mLauncher;
    }

    @Override
    public void setUiLauncher(UiLauncher launcher) {
        mLauncher = launcher;
    }

    private LaunchableConfigsHolder<?> getConfigs(String datumName) {
        return mConfigsMap.get(datumName);
    }
}
