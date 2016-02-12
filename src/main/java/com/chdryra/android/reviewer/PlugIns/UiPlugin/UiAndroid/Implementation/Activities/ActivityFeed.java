/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishingAction;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.FeedScreen;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

import java.util.ArrayList;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView implements
        DialogAlertFragment.DialogAlertListener,
        DeleteRequestListener{

    private FeedScreen mScreen;
    private ApplicationInstance mApp;

    @Override
    protected ReviewView createReviewView() {
        boolean launched = ApplicationLaunch.intitialiseLaunchIfNecessary(this,
                ApplicationLaunch.LaunchState.TEST);

        if(!launched) publishNewReviewfNecessary();

        mApp = ApplicationInstance.getInstance(this);

        FactoryFeedScreen feedScreenBuilder = getScreenBuilder();
        feedScreenBuilder.buildScreen(mApp.getAuthorsFeed());
        mScreen = feedScreenBuilder.getFeedScreen();

        return feedScreenBuilder.getView();
    }

    private void publishNewReviewfNecessary() {
        Intent intent = getIntent();
        String reviewId = intent.getStringExtra(PublishingAction.PUBLISHED);
        ArrayList<String> platforms = intent.getStringArrayListExtra(PublishingAction.PLATFORMS);
        if(reviewId == null || platforms == null || platforms.size() == 0) return;

        Review review = mApp.getReview(reviewId);

    }

    @NonNull
    private FactoryFeedScreen getScreenBuilder() {
        return new FactoryFeedScreen(mApp.getReviewViewAdapterFactory(),
                mApp.getLaunchableFactory(),
                mApp.getUiLauncher(),
                mApp.getConfigDataUi().getShareEditConfig().getLaunchable(),
                mApp.getConfigDataUi().getBuildReviewConfig().getLaunchable(),
                mApp.getReviewsFactory());
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
        mApp.deleteFromAuthorsFeed(reviewId);
    }

    @Override
    public void launch(LauncherUi launcher) {
        super.launch(launcher);
    }
}
