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
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api
        .LocationServicesApi;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ImageChooser;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
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

    ReviewBuilderAdapter<? extends GvDataList<?>> getReviewBuilderAdapter();

    FactoryReviews getReviewsFactory();

    SocialPlatformList getSocialPlatformList();

    ConfigUi getConfigUi();

    UiLauncher getUiLauncher();

    FactoryReviewView getLaunchableFactory();

    FactoryReviewViewParams getParamsFactory();

    FactoryGvData getGvDataFactory();

    LocationServicesApi getLocationServices();

    TagsManager getTagsManager();

    ReviewPublisher getPublisher();

    UsersManager getUsersManager();

    UserContext getUserContext();

    CurrentScreen getCurrentScreen();

    ReviewsFeed getFeed(DataAuthor author);

    <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType);

    ReviewBuilderAdapter<?> newReviewBuilderAdapter(@Nullable Review template);

    void discardReviewBuilderAdapter();

    Review executeReviewBuilder();

    void packReview(Review review, Bundle args);

    @Nullable
    Review unpackReview(Bundle args);

    void getReview(ReviewId id, ReviewsRepository.RepositoryCallback callback);

    ReviewDeleter newReviewDeleter(ReviewId id);

    void logout();

    void launchReview(ReviewId reviewId);

    void launchImageChooser(ImageChooser chooser, int requestCode);

    void launchFeed(DataAuthor author);

    void launchEditScreen(GvDataType<?> type);

    void setReturnResult(ActivityResultCode result);

    ReviewsListView newReviewsListView(ReviewNode node, boolean withMenu);

    LocationClient getLocationClient(LocationClient.Locatable locatable);
}

