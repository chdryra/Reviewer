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

import com.chdryra.android.reviewer.Application.Interfaces.ApplicationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Interfaces.ProfileSocial;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Commands.Factories
        .FactoryCommands;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewViewNode;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ReviewPack;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherAndroid;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
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
    private final ConverterGv mConverter;

    private CurrentScreen mCurrentScreen;
    private AuthorId mSessionUser;

    public UiSuiteAndroid(UiConfig uiConfig,
                          UiLauncherAndroid uiLauncher,
                          FactoryCommands commandsFactory,
                          FactoryReviewView viewFactory,
                          ConverterGv converter) {
        mUiConfig = uiConfig;
        mUiLauncher = uiLauncher;
        mUiConfig.setUiLauncher(mUiLauncher);
        mCommandsFactory = commandsFactory;
        mViewFactory = viewFactory;
        mConverter = converter;
    }

    @Override
    public ReviewView<?> newDataView(ReviewNode node, GvDataType<?> type) {
        return mViewFactory.newDataView(node, type);
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
    public ReviewViewNode newFeedView(RepositorySuite repository, ProfileSocial profile) {
        AuthorId user = mSessionUser != null ? mSessionUser : profile.getAuthorId();
        ReferencesRepository feed = repository.getFeed(profile);
        ReviewsSource repo = repository.getReviewsRepo();
        ReviewNode node = repo.getMetaReview(feed, user, Strings.ReviewsList.FEED);

        return mViewFactory.newFeedView(node);
    }

    @Override
    public ReviewView<?> newPublishView(ReviewEditor<?> editor,
                                        ReviewPublisher publisher,
                                        SocialPlatformList platforms,
                                        PlatformAuthoriser authoriser,
                                        PublishAction.PublishCallback callback) {
        return mViewFactory.newPublishView(editor, publisher, callback, platforms, authoriser);
    }

    @Override
    public ConverterGv getGvConverter() {
        return mConverter;
    }

    @Override
    public FactoryCommands getCommandsFactory() {
        return mCommandsFactory;
    }

    @Override
    public void returnToFeedScreen() {
        LaunchableConfig feed = getConfig().getFeed();
        feed.launch(new UiLauncherArgs(feed.getDefaultRequestCode()).setClearBackStack());
        getCurrentScreen().close();
    }

    public void setActivity(Activity activity) {
        mCurrentScreen = new CurrentScreenAndroid(activity);
        mUiLauncher.setActivity(activity);
    }

    public void setSession(UserSession session) {
        mUiLauncher.setSession(session);
        mSessionUser = session.getAuthorId();
    }

    public void setApplication(ApplicationSuite app) {
        mCommandsFactory.setApp(app);
    }

    ReviewPack unpackReview(Bundle args) {
        return mUiLauncher.unpackReview(args);
    }

    @Nullable
    ReviewNode unpackNode(Bundle args) {
        return mUiLauncher.unpackNode(args);
    }

    @Nullable
    ReviewView<?> unpackView(Intent i) {
        return mUiLauncher.unpackView(i);
    }
}
