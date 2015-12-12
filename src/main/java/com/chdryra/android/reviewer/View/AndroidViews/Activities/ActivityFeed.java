/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.AndroidViews.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.FeedScreen;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView
        implements DialogAlertFragment.DialogAlertListener,
        FeedScreen.DeleteRequestListener{
    private FeedScreen mScreen;
    private ReviewsFeedMutable mAuthorsFeed;

    //Overridden
    @Override
    protected ReviewView createReviewView() {
        ApplicationLaunch.intitialiseSingletons(this, ApplicationLaunch.LaunchState.TEST);

        ApplicationInstance app = ApplicationInstance.getInstance(this);

        mAuthorsFeed = app.getAuthorsFeed();

        FactoryFeedScreen feedScreenBuilder = getScreenBuilder(app);
        feedScreenBuilder.buildScreen(mAuthorsFeed, this);
        mScreen = feedScreenBuilder.getFeedScreen();

        return feedScreenBuilder.getView();
    }

    @NonNull
    private FactoryFeedScreen getScreenBuilder(ApplicationInstance app) {
        return new FactoryFeedScreen(app.getReviewViewAdapterFactory(), app.getLaunchableFactory(),
        app.getUiLauncher(), app.getReviewsFactory(), app.getConfigDataUi().getBuildReviewConfig());
    }

    //Dialogs send results to host activity
    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mScreen.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mScreen.onAlertPositive(requestCode, args);
    }

    @Override
    public void onDeleteRequested(ReviewId reviewId) {
        mAuthorsFeed.removeReview(reviewId);
    }
}
