/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.View.ActivitiesFragments;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.View.Launcher.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderAuthorFeedScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.ReviewViewModel.Implementation.FeedScreen;
import com.chdryra.android.reviewer.View.ReviewViewModel.Interfaces.ReviewView;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView
        implements DialogAlertFragment.DialogAlertListener{
    private FeedScreen mScreen;

    //Overridden
    @Override
    protected ReviewView createReviewView() {
        ApplicationLaunch.intitialiseSingletons(this, ApplicationLaunch.LaunchState.TEST);

        ApplicationInstance app = ApplicationInstance.getInstance(this);

        FactoryReviews reviewFactory = app.getReviewsFactory();
        FactoryReviewViewAdapter adapterFactory = app.getReviewViewAdapterFactory();
        FactoryLaunchableUi launchableFactory = app.getLaunchableFactory();

        BuilderAuthorFeedScreen feedScreenBuilder =
                new BuilderAuthorFeedScreen(adapterFactory, launchableFactory, reviewFactory);

        ReviewsProvider feed = app.getReviewsProvider();
        BuilderChildListView childListBuilder = app.getBuilderChildListScreen();
        feedScreenBuilder.buildScreen(feed, childListBuilder);

        mScreen = feedScreenBuilder.getFeedScreen();

        return feedScreenBuilder.getView();
    }

    //Overridden
    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mScreen.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mScreen.onAlertPositive(requestCode, args);
    }
}
