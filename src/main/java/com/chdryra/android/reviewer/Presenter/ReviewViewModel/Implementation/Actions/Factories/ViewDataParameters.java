/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.Factories;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands
        .Implementation.Command;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

public class ViewDataParameters<T extends GvData> {
    private final GvDataType<T> mDataType;
    private final FactoryReviewView mFactoryView;
    private final FactoryCommands mFactoryCommands;
    private final ReviewStamp mStamp;
    private final AuthorsRepository mRepo;
    private final UiLauncher mLauncher;

    private Command mBannerClick;
    private LaunchableConfig mGridItemConfig;
    private String mButtonTitle;

    public ViewDataParameters(GvDataType<T> dataType,
                              FactoryReviewView factoryView,
                              FactoryCommands factoryCommands,
                              ReviewStamp stamp,
                              AuthorsRepository repo,
                              UiLauncher launcher) {
        mDataType = dataType;
        mButtonTitle = dataType.getDataName();
        mFactoryView = factoryView;
        mFactoryCommands = factoryCommands;
        mStamp = stamp;
        mRepo = repo;
        mLauncher = launcher;
    }

    public ViewDataParameters<T> setBannerClick(@Nullable Command bannerClick) {
        mBannerClick = bannerClick;
        return this;
    }

    public ViewDataParameters<T> setGridItemConfig(@Nullable LaunchableConfig gridItemConfig) {
        mGridItemConfig = gridItemConfig;
        return this;
    }

    public ViewDataParameters<T> setButtonTitle(String buttonTitle) {
        mButtonTitle = buttonTitle;
        return this;
    }

    public GvDataType<T> getDataType() {
        return mDataType;
    }

    public FactoryReviewView getFactoryView() {
        return mFactoryView;
    }

    public FactoryCommands getFactoryCommands() {
        return mFactoryCommands;
    }

    public ReviewStamp getStamp() {
        return mStamp;
    }

    public AuthorsRepository getRepo() {
        return mRepo;
    }

    public UiLauncher getLauncher() {
        return mLauncher;
    }

    @Nullable
    public Command getBannerClick() {
        return mBannerClick;
    }

    @Nullable
    public LaunchableConfig getGridItemConfig() {
        return mGridItemConfig;
    }

    public String getButtonTitle() {
        return mButtonTitle;
    }
}
