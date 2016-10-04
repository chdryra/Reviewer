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
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
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
    private FactoryReviews mReviewsFactory;
    private RepositorySuite mRepo;
    private AuthorId mSessionUser;

    public UiSuiteAndroid(UiConfig uiConfig, UiLauncherAndroid uiLauncher,
                          FactoryReviewView viewFactory, FactoryReviews reviewsFactory, RepositorySuite repo) {
        mUiConfig = uiConfig;
        mUiLauncher = uiLauncher;
        mViewFactory = viewFactory;
        mReviewsFactory = reviewsFactory;
        mRepo = repo;
    }

    @Override
    public CurrentScreen getCurrentScreen() {
        return mCurrentScreen;
    }

    @Override
    public UiConfig getConfig() {
        return mUiConfig;
    }

    @Override
    public UiLauncher getLauncher() {
        return mUiLauncher;
    }

    @Override
    public ReviewsListView newFeedView(SocialProfile profile) {
        ReferencesRepository feed = mRepo.getFeed(profile);
        AuthorId user = mSessionUser != null ? mSessionUser : profile.getAuthorId();
        ReviewNodeRepo node = mReviewsFactory.createFeed(user, mRepo.getName(user), feed);

        return mViewFactory.newFeedView(node);
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
        mSessionUser = session.getAuthorId();
    }
}