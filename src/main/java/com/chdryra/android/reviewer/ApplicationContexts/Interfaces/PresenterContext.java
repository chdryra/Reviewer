/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.FactoryUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface PresenterContext {
    FactoryReviewView getReviewViewLaunchableFactory();

    FactoryGvData getGvDataFactory();

    FactoryReviews getReviewsFactory();

    SocialPlatformList getSocialPlatformList();

    ConfigUi getConfigUi();

    FactoryUiLauncher getLauncherFactory();

    ReviewsListView newReviewsListView(ReviewNode node, boolean withMenu);

    void asMetaReview(ReviewId reviewId, ReviewsSource.ReviewsSourceCallback callback);

    ReviewBuilderAdapter<?> newReviewBuilderAdapter(@Nullable Review template);

    void discardReviewBuilderAdapter();

    ReviewBuilderAdapter<?> getReviewBuilderAdapter();

    <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType);

    Review executeReviewBuilder();

    FactoryReviewsFeed getFeedFactory();

    void getReview(ReviewId id, ReviewsRepository.RepositoryCallback callback);

    TagsManager getTagsManager();

    ReviewPublisher getReviewPublisher();

    ReviewsRepositoryMutable getBackendRepository();

    UsersManager getUsersManager();

    ReviewDeleter newReviewDeleter(ReviewId id);
}
