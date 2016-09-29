/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.ReviewLauncher.ReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationInstance {
    String APP_NAME = "Teeqr";

    //Users Suite
    UserSession getUserSession();

    UsersManager getUsersManager();

    void logout();

    //LocationsSuite
    LocationServicesApi getLocationServices();

    LocationClient newLocationClient();

    //UISuite
    CurrentScreen getCurrentScreen();

    ConfigUi getConfigUi();

    UiLauncher newUiLauncher();

    ReviewsListView newFeedView();

    ReviewLauncher newReviewLauncher();

    //EditSuite
    ReviewEditor<?> newReviewEditor(@Nullable Review template);

    ReviewEditor<?> getReviewEditor();

    Review executeReviewEditor();

    void discardReviewEditor();

    //Social suite
    SocialPlatformList getSocialPlatformList();

    SocialProfile getSocialProfile();

    ReviewView<?> newPublishScreen(PlatformAuthoriser authoriser, PublishAction.PublishCallback callback);

    ReviewPublisher getPublisher();

    FactoryReviews getReviewsFactory();

    //Repo suite
    void getReview(ReviewId id, RepositoryCallback callback);

    ReferencesRepository getReviews(AuthorId authorId);

    ReferencesRepository getUsersFeed();

    ReviewDeleter newReviewDeleter(ReviewId id);

    TagsManager getTagsManager();

    //UtilSuite
    @Nullable
    Review unpackReview(Bundle args);

    void setReturnResult(ActivityResultCode result);


}

