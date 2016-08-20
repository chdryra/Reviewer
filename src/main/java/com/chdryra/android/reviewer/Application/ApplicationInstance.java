/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.Authentication.Interfaces.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationInstance {
    String APP_NAME = "Teeqr";

    UserSession getUserSession();

    void logout();

    UsersManager getUsersManager();

    LocationServicesApi getLocationServices();

    LocationClient getLocationClient(LocationClient.Locatable locatable);

    SocialPlatformList getSocialPlatformList();

    CurrentScreen getCurrentScreen();

    ConfigUi getConfigUi();

    UiLauncher getUiLauncher();

    ReviewBuilderAdapter<?> newReviewBuilderAdapter(@Nullable Review template);

    ReviewBuilderAdapter<? extends GvDataList<?>> getReviewBuilderAdapter();

    Review executeReviewBuilder();

    void discardReviewBuilderAdapter();

    FactoryReviews getReviewsFactory();

    FactoryReviewViewParams getViewParamsFactory();

    FactoryGvData getGvDataFactory();

    TagsManager getTagsManager();

    ReviewPublisher getPublisher();

    void getReview(ReviewId id, RepositoryCallback callback);

    ReferencesRepository getReviews(AuthorId authorId);

    ReviewDeleter newReviewDeleter(ReviewId id);

    void packReview(Review review, Bundle args);

    @Nullable
    Review unpackReview(Bundle args);

    void launchReview(ReviewId reviewId);

    void launchImageChooser(ImageChooser chooser, int requestCode);

    void launchFeed(AuthorId authorId);

    void launchEditScreen(GvDataType<? extends GvDataParcelable> type);

    void setReturnResult(ActivityResultCode result);

    ReviewsListView newReviewsListView(ReviewNode node, boolean withMenu);
}

