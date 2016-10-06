/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.Configs.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

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
public final class UiConfigImpl implements UiConfig {
    private final Map<String, DataConfigs<? extends GvData>> mConfigsMap;
    private final LaunchableConfig mLogin;
    private final LaunchableConfig mSignUp;
    private final LaunchableConfig mFeed;
    private final LaunchableConfig mBuildReview;
    private final LaunchableConfig mEditOnMap;
    private final LaunchableConfig mPublish;
    private final LaunchableConfig mOptions;

    private UiLauncher mLauncher;

    public UiConfigImpl(Iterable<? extends DataConfigs<?>> dataConfigs,
                        LaunchableConfig login,
                        LaunchableConfig signUp,
                        LaunchableConfig feed,
                        LaunchableConfig buildReview,
                        LaunchableConfig editOnMap,
                        LaunchableConfig publish,
                        LaunchableConfig options) {
        mConfigsMap = new HashMap<>();
        for (DataConfigs<?> dataConfig : dataConfigs) {
            mConfigsMap.put(dataConfig.getGvDataType().getDatumName(), dataConfig);
        }

        mLogin = login;
        mSignUp = signUp;
        mFeed = feed;
        mBuildReview = buildReview;
        mEditOnMap = editOnMap;
        mPublish = publish;
        mOptions = options;
    }

    @Override
    public LaunchableConfig getViewer(String datumName) {
        return getConfigs(datumName).getViewerConfig();
    }

    @Override
    public LaunchableConfig getEditor(String datumName) {
        return getConfigs(datumName).getEditorConfig();
    }

    @Override
    public LaunchableConfig getAdder(String datumName) {
        return getConfigs(datumName).getAdderConfig();
    }

    @Override
    public LaunchableConfig getLogin() {
        return mLogin;
    }

    @Override
    public LaunchableConfig getSignUp() {
        return mSignUp;
    }

    @Override
    public LaunchableConfig getFeed() {
        return mFeed;
    }

    @Override
    public LaunchableConfig getBuildReview() {
        return mBuildReview;
    }

    @Override
    public LaunchableConfig getMapEditor() {
        return mEditOnMap;
    }

    @Override
    public LaunchableConfig getPublish() {
        return mPublish;
    }

    @Override
    public LaunchableConfig getReviewOptions() {
        return mOptions;
    }

    @Override
    public UiLauncher getUiLauncher() {
        return mLauncher;
    }

    @Override
    public void setUiLauncher(UiLauncher launcher) {
        mLauncher = launcher;
        mLogin.setLauncher(mLauncher);
        mSignUp.setLauncher(mLauncher);
        mFeed.setLauncher(mLauncher);
        mBuildReview.setLauncher(mLauncher);
        mEditOnMap.setLauncher(mLauncher);
        mPublish.setLauncher(mLauncher);
        mOptions.setLauncher(mLauncher);
        for(DataConfigs<?> holder : mConfigsMap.values()) {
            holder.setLauncher(mLauncher);
        }
    }

    private DataConfigs<?> getConfigs(String datumName) {
        return mConfigsMap.get(datumName);
    }
}
