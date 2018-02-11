/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Actions.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.Comparators.ComparatorCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.ReviewStamp;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryLaunchCommands;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Commands.Implementation.CommandList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;

public class ActionsParameters<T extends GvData> {
    private final GvDataType<T> mDataType;
    private final FactoryReviewView mFactoryView;
    private final FactoryLaunchCommands mFactoryLaunchCommands;
    private final AuthorsRepo mRepo;
    private final UiLauncher mLauncher;

    private LaunchableConfig mGridItemConfig;
    private CommandList mContextCommands;
    private ComparatorCollection<? super T> mComparators;
    private ReviewStamp mStamp;

    public ActionsParameters(GvDataType<T> dataType,
                             FactoryReviewView factoryView,
                             FactoryLaunchCommands factoryLaunchCommands,
                             AuthorsRepo repo,
                             UiLauncher launcher) {
        mDataType = dataType;
        mFactoryView = factoryView;
        mFactoryLaunchCommands = factoryLaunchCommands;
        mRepo = repo;
        mLauncher = launcher;
    }

    public GvDataType<T> getDataType() {
        return mDataType;
    }

    public FactoryReviewView getFactoryView() {
        return mFactoryView;
    }

    public FactoryLaunchCommands getFactoryLaunchCommands() {
        return mFactoryLaunchCommands;
    }

    @Nullable
    public ReviewStamp getStamp() {
        return mStamp;
    }

    public ActionsParameters<T> setStamp(ReviewStamp stamp) {
        mStamp = stamp;
        return this;
    }

    public AuthorsRepo getRepo() {
        return mRepo;
    }

    public UiLauncher getLauncher() {
        return mLauncher;
    }

    @Nullable
    public ComparatorCollection<? super T> getComparators() {
        return mComparators;
    }

    public ActionsParameters<T> setComparators(ComparatorCollection<? super T> comparators) {
        mComparators = comparators;
        return this;
    }

    @Nullable
    public LaunchableConfig getGridItemConfig() {
        return mGridItemConfig;
    }

    public ActionsParameters<T> setGridItemConfig(@Nullable LaunchableConfig gridItemConfig) {
        mGridItemConfig = gridItemConfig;
        return this;
    }

    public CommandList getContextCommands() {
        return mContextCommands;
    }

    public ActionsParameters<T> setContextCommands(CommandList contextCommands) {
        mContextCommands = contextCommands;
        return this;
    }

}
