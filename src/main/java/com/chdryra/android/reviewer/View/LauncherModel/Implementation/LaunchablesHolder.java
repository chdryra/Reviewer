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
import com.chdryra.android.reviewer.View.Configs.AddEditViewClasses;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUiAlertable;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchablesList;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 25/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class LaunchablesHolder implements LaunchablesList {
    private final ArrayList<AddEditViewClasses<?>> mDataLaunchables;
    private final Class<? extends LaunchableUi> mLogin;
    private final Class<? extends LaunchableUi> mSignUp;
    private final Class<? extends LaunchableUi> mUsersFeed;
    private final Class<? extends LaunchableUi> mFeed;
    private final Class<? extends LaunchableUi> mReviewBuilder;
    private final Class<? extends LaunchableUi> mMapEditor;
    private final Class<? extends LaunchableUi> mShare;
    private final Class<? extends LaunchableUiAlertable> mShareEdit;

    //TODO make this independent of Android
    private final Class<? extends Activity> mDefaultReviewViewActivity;
    private final Class<? extends Activity> mReviewsListActivity;

    public LaunchablesHolder(Class<? extends LaunchableUi> login,
                             Class<? extends LaunchableUi> signUp,
                             Class<? extends LaunchableUi> usersFeed,
                             Class<? extends LaunchableUi> feed,
                             Class<? extends LaunchableUi> reviewBuilder,
                             Class<? extends LaunchableUi> mapEditor,
                             Class<? extends LaunchableUi> share,
                             Class<? extends LaunchableUiAlertable> shareEdit,
                             Class<? extends Activity> defaultReviewViewActivity,
                             Class<? extends Activity> reviewsListActivity) {
        mLogin = login;
        mSignUp = signUp;
        mUsersFeed = usersFeed;
        mFeed = feed;
        mReviewBuilder = reviewBuilder;
        mMapEditor = mapEditor;
        mShare = share;
        mShareEdit = shareEdit;
        mDefaultReviewViewActivity = defaultReviewViewActivity;
        mReviewsListActivity = reviewsListActivity;
        mDataLaunchables = new ArrayList<>();
    }

    protected <T extends GvData> void addDataClasses(AddEditViewClasses<T> classes) {
        mDataLaunchables.add(classes);
    }

    @Override
    public Class<? extends LaunchableUi> getLoginUi() {
        return mLogin;
    }

    @Override
    public Class<? extends LaunchableUi> getSignUpUi() {
        return mSignUp;
    }

    @Override
    public Class<? extends LaunchableUi> getUsersFeedUi() {
        return mUsersFeed;
    }

    @Override
    public Class<? extends LaunchableUi> getFeedUi() {
        return mFeed;
    }

    @Override
    public Class<? extends LaunchableUi> getReviewBuilderUi() {
        return mReviewBuilder;
    }

    @Override
    public Class<? extends LaunchableUi> getMapEditorUi() {
        return mMapEditor;
    }

    @Override
    public Class<? extends LaunchableUi> getShareReviewUi() {
        return mShare;
    }

    @Override
    public Class<? extends LaunchableUiAlertable> getShareEditReviewUi() {
        return mShareEdit;
    }

    @Override
    public ArrayList<AddEditViewClasses<?>> getDataLaunchableUis() {
        return mDataLaunchables;
    }

    @Override
    public Class<? extends Activity> getDefaultReviewViewActivity() {
        return mDefaultReviewViewActivity;
    }

    @Override
    public Class<? extends Activity> getReviewsListActivity() {
        return mReviewsListActivity;
    }
}
