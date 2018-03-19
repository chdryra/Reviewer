/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.View.Configs.Implementation;

import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates the range of dialogs and activities available to return to the user when the user
 * chooses to add, edit or display the data.
 * <p/>
 * <p>
 * Retrieves relevant add, edit and display UIs for each {@link com.chdryra.android.reviewer
 * .GVReviewDataList.GVType} from
 * {@link com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid.AndroidLaunchables}
 * and packages them with request codes and tags so that they can be appropriately launched
 * by whichever UI needs them in response to a user interaction.
 * </p>
 */
public final class UiConfigImpl implements UiConfig {
    private final Map<String, DataConfigs<? extends GvData>> mDataConfigsMap;
    private final Map<String, LaunchableConfig> mBespokeEditorsMap;
    private final Map<String, LaunchableConfig> mBespokeDataViewersMap;
    private final Map<String, LaunchableConfig> mBespokeDatumViewersMap;
    private final LaunchableConfig mLogin;
    private final LaunchableConfig mProfile;
    private final LaunchableConfig mFeed;
    private final LaunchableConfig mPublish;
    private final LaunchableConfig mOptions;

    private UiLauncher mLauncher;

    public UiConfigImpl(Iterable<? extends DataConfigs<?>> dataConfigs,
                        Map<String, LaunchableConfig> bespokeEditorsMap,
                        Map<String, LaunchableConfig> bespokeDataViewersMap,
                        Map<String, LaunchableConfig> bespokeDatumViewersMap,
                        LaunchableConfig login,
                        LaunchableConfig profile,
                        LaunchableConfig feed,
                        LaunchableConfig publish,
                        LaunchableConfig options) {
        mDataConfigsMap = new HashMap<>();
        for (DataConfigs<?> dataConfig : dataConfigs) {
            mDataConfigsMap.put(dataConfig.getGvDataType().getDatumName(), dataConfig);
        }

        mBespokeEditorsMap = bespokeEditorsMap;
        mBespokeDataViewersMap = bespokeDataViewersMap;
        mBespokeDatumViewersMap = bespokeDatumViewersMap;

        mLogin = login;
        mProfile = profile;
        mFeed = feed;
        mPublish = publish;
        mOptions = options;
    }

    @Override
    public boolean hasConfig(String datumName) {
        return getConfigs(datumName) != null;
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
    public LaunchableConfig getBespokeViewer(String datumName) {
        return mBespokeDataViewersMap.get(datumName);
    }

    @Override
    public LaunchableConfig getBespokeDatumViewer(String datumName) {
        LaunchableConfig config = mBespokeDatumViewersMap.get(datumName);
        return config != null ? config : getViewer(datumName);
    }

    @Override
    public LaunchableConfig getBespokeEditor(String datumName) {
        LaunchableConfig config = mBespokeEditorsMap.get(datumName);
        return config != null ? config : getEditor(datumName);
    }

    @Override
    public LaunchableConfig getLogin() {
        return mLogin;
    }

    @Override
    public LaunchableConfig getProfile() {
        return mProfile;
    }

    @Override
    public LaunchableConfig getFeed() {
        return mFeed;
    }

    @Override
    public LaunchableConfig getPublish() {
        return mPublish;
    }

    @Override
    public LaunchableConfig getOptions() {
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
        mProfile.setLauncher(mLauncher);
        mFeed.setLauncher(mLauncher);
        mPublish.setLauncher(mLauncher);
        mOptions.setLauncher(mLauncher);
        for (DataConfigs<?> holder : mDataConfigsMap.values()) {
            holder.setLauncher(mLauncher);
        }
        for (LaunchableConfig config : mBespokeEditorsMap.values()) {
            config.setLauncher(launcher);
        }
        for (LaunchableConfig config : mBespokeDataViewersMap.values()) {
            config.setLauncher(launcher);
        }
        for (LaunchableConfig config : mBespokeDatumViewersMap.values()) {
            config.setLauncher(launcher);
        }
    }

    private DataConfigs<?> getConfigs(String datumName) {
        return mDataConfigsMap.get(datumName);
    }
}
