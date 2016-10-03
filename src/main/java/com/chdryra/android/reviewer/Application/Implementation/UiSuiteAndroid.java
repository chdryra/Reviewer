/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewNodeRepo;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.View.Configs.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncherAndroid;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class UiSuiteAndroid implements UiSuite{
    private CurrentScreen mCurrentScreen;
    private UiConfig mUiConfig;
    private UiLauncherAndroid mUiLauncher;
    private FactoryReviewView mViewFactory;
    private AuthorsRepository mRepo;

    public UiSuiteAndroid(UiConfig uiConfig, FactoryReviewView viewFactory, AuthorsRepository repo) {
        mUiConfig = uiConfig;
        mRepo = repo;
        mViewFactory = viewFactory;
    }

    @Override
    public CurrentScreen getCurrentScreen() {
        return mCurrentScreen;
    }

    @Override
    public UiConfig getUiConfig() {
        return mUiConfig;
    }

    @Override
    public UiLauncher getUiLauncher() {
        return mUiLauncher;
    }

    @Override
    public ReviewsListView newFeedView(SocialProfile profile) {
        ReferencesRepository feed = getUsersFeed();
        ReviewNodeRepo node = getReviewsFactory().createFeed(getSessionUser(), feed, mRepo);

        return mAppContext.newFeedView(node);
    }

    @Override
    public ReviewView<?> newPublishView(ReviewViewAdapter<?> builder,
                                        SocialPlatformList platforms,
                                        PlatformAuthoriser authoriser,
                                        ReviewPublisher publisher,
                                        PublishAction.PublishCallback callback) {
        return mViewFactory.newPublishView(builder, platforms, authoriser, publisher, callback);
    }

    public void setActivity(Activity activity) {
        mCurrentScreen = new CurrentScreenAndroid(activity);
        mUiLauncher.setActivity(activity);
    }

    public void setSession(UserSession session) {
        mUiLauncher.setSession(session);
    }

    public void setLauncher(UiLauncherAndroid launcher) {
        mUiLauncher = launcher;
        mUiConfig.setUiLauncher(mUiLauncher);
    }
}
