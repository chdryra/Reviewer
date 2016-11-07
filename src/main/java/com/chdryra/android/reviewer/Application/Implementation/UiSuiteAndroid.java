/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories.FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewNodeRepo;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewNode;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherAndroid;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class UiSuiteAndroid implements UiSuite{
    private final UiConfig mUiConfig;
    private final UiLauncherAndroid mUiLauncher;
    private final FactoryCommands mCommandsFactory;
    private final FactoryReviewView mViewFactory;
    private final FactoryReviews mReviewsFactory;
    private final ConverterGv mConverter;

    private CurrentScreen mCurrentScreen;
    private AuthorId mSessionUser;

    public UiSuiteAndroid(UiConfig uiConfig,
                          UiLauncherAndroid uiLauncher,
                          FactoryCommands commandsFactory,
                          FactoryReviewView viewFactory,
                          FactoryReviews reviewsFactory,
                          ConverterGv converter) {
        mUiConfig = uiConfig;
        mUiLauncher = uiLauncher;
        mUiConfig.setUiLauncher(mUiLauncher);
        mCommandsFactory = commandsFactory;
        mViewFactory = viewFactory;
        mReviewsFactory = reviewsFactory;
        mConverter = converter;
    }

    @Override
    public ReviewView<?> newDataView(ReviewNode node, GvDataType<?> type) {
        ReviewViewAdapter<?> adapter = mViewFactory.getAdapterFactory()
                .newReviewDataAdapter(node, type);
        return mViewFactory.newDefaultView(adapter);

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
    public ReviewViewNode newFeedView(RepositorySuite repository, SocialProfile profile) {
        ReferencesRepository feed = repository.getFeed(profile);
        AuthorId user = mSessionUser != null ? mSessionUser : profile.getAuthorId();
        ReviewNodeRepo node = mReviewsFactory.createFeed(user, repository.getName(user), feed);

        return mViewFactory.newFeedView(node);
    }

    @Override
    public ReviewView<?> newPublishView(ReviewEditor<?> editor,
                                        ReviewPublisher publisher, SocialPlatformList platforms,
                                        PlatformAuthoriser authoriser,
                                        PublishAction.PublishCallback callback) {
        return mViewFactory.newPublishView(editor, publisher, platforms, authoriser, callback);
    }

    @Override
    public ConverterGv getGvConverter() {
        return mConverter;
    }

    @Override
    public FactoryCommands getCommandsFactory() {
        return mCommandsFactory;
    }

    public void setActivity(Activity activity) {
        mCurrentScreen = new CurrentScreenAndroid(activity);
        mUiLauncher.setActivity(activity);
    }

    public void setSession(UserSession session) {
        mUiLauncher.setSession(session);
        mSessionUser = session.getAuthorId();
    }

    @Nullable
    Review unpackReview(Bundle args) {
        return mUiLauncher.unpackReview(args);
    }

    @Nullable
    ReviewView<?> unpackView(Intent i) {
        return mUiLauncher.unpackView(i);
    }
}
