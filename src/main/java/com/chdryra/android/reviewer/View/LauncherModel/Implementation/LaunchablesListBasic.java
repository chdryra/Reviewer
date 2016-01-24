/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.Configs.AddEditViewClasses;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchablesList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class LaunchablesListBasic implements LaunchablesList {
    private final ArrayList<AddEditViewClasses<?>> mDataLaunchables = new ArrayList<>();
    private final Class<? extends LaunchableUi> mReviewBuilderLaunchable;
    private final Class<? extends LaunchableUi> mMapEditorLaunchable;
    private final Class<? extends LaunchableUi> mShareLaunchable;

    public LaunchablesListBasic(Class<? extends LaunchableUi> reviewBuilderLaunchable, Class<?
            extends
            LaunchableUi> mapEditorLaunchable, Class<? extends LaunchableUi> shareLaunchable) {
        mReviewBuilderLaunchable = reviewBuilderLaunchable;
        mMapEditorLaunchable = mapEditorLaunchable;
        mShareLaunchable = shareLaunchable;
    }

    protected <T extends GvData> void addDataClasses(AddEditViewClasses<T> classes) {
        mDataLaunchables.add(classes);
    }

    @Override
    public Class<? extends LaunchableUi> getReviewBuilderUi() {
        return mReviewBuilderLaunchable;
    }

    @Override
    public Class<? extends LaunchableUi> getMapEditorUi() {
        return mMapEditorLaunchable;
    }

    @Override
    public Class<? extends LaunchableUi> getShareReviewUi() {
        return mShareLaunchable;
    }

    @Override
    public ArrayList<AddEditViewClasses<?>> getDataLaunchableUis() {
        return mDataLaunchables;
    }
}
