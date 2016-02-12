/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import android.app.Activity;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface PresenterContext {
    FactoryReviewViewLaunchable getReviewViewLaunchableFactory();

    FactoryReviewViewAdapter getReviewViewAdapterFactory();

    FactoryGvData getGvDataFactory();

    FactoryReviews getReviewsFactory();

    SocialPlatformList getSocialPlatformList();

    ConfigUi getConfigDataUi();

    LaunchableUiLauncher getUiLauncher();

    void launchReview(Activity activity, ReviewId reviewId);

    ReviewBuilderAdapter<?> newReviewBuilderAdapter();

    void discardReviewBuilderAdapter();

    ReviewBuilderAdapter<?> getReviewBuilderAdapter();

    <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType);

    Review publishReviewBuilder();

    ReviewsFeed getAuthorsFeed();

    void deleteFromAuthorsFeed(ReviewId id);

    Review getReview(ReviewId id);

    TagsManager getTagsManager();
}
