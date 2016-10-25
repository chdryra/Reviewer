/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.View.LauncherModel.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.View.Configs.Implementation.DataLaunchables;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchablesList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchablesHolder implements LaunchablesList {
    private final ArrayList<DataLaunchables<?>> mDataLaunchables;
    private final Class<? extends LaunchableUi> mLogin;
    private final Class<? extends LaunchableUi> mSignUp;
    private final Class<? extends LaunchableUi> mFeed;
    private final Class<? extends LaunchableUi> mReviewBuild;
    private final Class<? extends LaunchableUi> mReviewFormatted;
    private final Class<? extends LaunchableUi> mMapEdit;
    private final Class<? extends LaunchableUi> mPublish;
    private final Class<? extends LaunchableUi> mOptions;

    //TODO make this independent of Android
    private final Class<? extends Activity> mDefaultActivity;

    public LaunchablesHolder(Class<? extends LaunchableUi> login,
                             Class<? extends LaunchableUi> signUp,
                             Class<? extends LaunchableUi> feed,
                             Class<? extends LaunchableUi> reviewBuild,
                             Class<? extends LaunchableUi> reviewFormatted,
                             Class<? extends LaunchableUi> mapEdit,
                             Class<? extends LaunchableUi> publish,
                             Class<? extends LaunchableUi> options,
                             Class<? extends Activity> defaultActivity) {
        mLogin = login;
        mSignUp = signUp;
        mFeed = feed;
        mReviewBuild = reviewBuild;
        mReviewFormatted = reviewFormatted;
        mMapEdit = mapEdit;
        mPublish = publish;
        mOptions = options;
        mDefaultActivity = defaultActivity;
        mDataLaunchables = new ArrayList<>();
    }

    protected <T extends GvData> void addDataClasses(DataLaunchables<T> classes) {
        mDataLaunchables.add(classes);
    }

    @Override
    public Class<? extends LaunchableUi> getLogin() {
        return mLogin;
    }

    @Override
    public Class<? extends LaunchableUi> getSignUp() {
        return mSignUp;
    }

    @Override
    public Class<? extends LaunchableUi> getFeed() {
        return mFeed;
    }

    @Override
    public Class<? extends LaunchableUi> getReviewBuild() {
        return mReviewBuild;
    }

    @Override
    public Class<? extends LaunchableUi> getReviewFormatted() {
        return mReviewFormatted;
    }

    @Override
    public Class<? extends LaunchableUi> getMapEdit() {
        return mMapEdit;
    }

    @Override
    public Class<? extends LaunchableUi> getPublish() {
        return mPublish;
    }

    @Override
    public Class<? extends LaunchableUi> getReviewOptions() {
        return mOptions;
    }

    @Override
    public ArrayList<DataLaunchables<?>> getDataLaunchables() {
        return mDataLaunchables;
    }

    @Override
    public Class<? extends Activity> getDefaultActivity() {
        return mDefaultActivity;
    }
}
