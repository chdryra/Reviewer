/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.View.Configs.Implementation;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Encapsulates add, edit, view configs for a given
 * {@link GvDataType}.
 */
public class DataConfigs<T extends GvData> {
    private static final int ADD = RequestCodeGenerator.getCode(DataConfigs.class, "Add");
    private static final int EDIT = RequestCodeGenerator.getCode(DataConfigs.class, "Edit");
    private static final int VIEW = RequestCodeGenerator.getCode(DataConfigs.class, "View");
    private final GvDataType<T> mDataType;
    private final LaunchableConfig mAdd;
    private final LaunchableConfig mEdit;
    private final LaunchableConfig mView;

    public DataConfigs(DataLaunchables<T> launchables) {
        mDataType = launchables.getGvDataType();
        mAdd = new LaunchableConfigImpl(launchables.getAddClass(), ADD);
        mEdit = new LaunchableConfigImpl(launchables.getEditClass(), EDIT);
        mView = new LaunchableConfigImpl(launchables.getViewClass(), VIEW);
    }

    public GvDataType<T> getGvDataType() {
        return mDataType;
    }

    public void setLauncher(UiLauncher launcher) {
        if (mAdd != null) mAdd.setLauncher(launcher);
        if (mEdit != null) mEdit.setLauncher(launcher);
        if (mView != null) mView.setLauncher(launcher);
    }

    LaunchableConfig getAdderConfig() {
        return mAdd;
    }

    LaunchableConfig getEditorConfig() {
        return mEdit;
    }

    LaunchableConfig getViewerConfig() {
        return mView;
    }
}
