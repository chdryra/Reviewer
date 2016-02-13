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
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.FeedScreen;
import com.chdryra.android.reviewer.Social.Implementation.BatchSocialPublisher;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView implements
        DialogAlertFragment.DialogAlertListener,
        DeleteRequestListener,
        BatchSocialPublisher.BatchPublisherListener{

    private FeedScreen mScreen;
    private ApplicationInstance mApp;

    @Override
    protected ReviewView createReviewView() {
        boolean launched = ApplicationLaunch.intitialiseLaunchIfNecessary(this,
                ApplicationLaunch.LaunchState.TEST);

        mApp = ApplicationInstance.getInstance(this);

        if(!launched) publishNewReviewfNecessary();

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

        BatchSocialPublisher publisher = getPublisher(platforms);
        publisher.publish(mApp.getReview(reviewId), mApp.getTagsManager(), this, this);
    }

    @NonNull
    private BatchSocialPublisher getPublisher(ArrayList<String> platforms) {
        SocialPlatformList platformList = mApp.getSocialPlatformList();
        Collection<SocialPublisher> publishers = new ArrayList<>();
        for(SocialPlatform platform : platformList) {
            if(platforms.contains(platform.getName())) publishers.add(platform.getPublisher());
        }

        return new BatchSocialPublisher(publishers);
    }

    @Override
    public void onPublished(Collection<PublishResults> results) {
        ArrayList<String> platformsOk = new ArrayList<>();
        ArrayList<String> platformsNotOk = new ArrayList<>();
        int numFollowers = 0;
        for(PublishResults result : results) {
            if(result.isSuccessful()) {
                platformsOk.add(result.getPublisherName());
                numFollowers += result.getFollowers();
            } else {
                platformsNotOk.add(result.getPublisherName());
            }
        }

        String num = String.valueOf(numFollowers);
        String followers = numFollowers == 1 ? " follower" : " followers";
        String message = "Published to " + num + followers + " on " +
                StringUtils.join(platformsOk.toArray(), ", ");

        if(platformsNotOk.size() > 1) {
            message += "\nProblems publishing to " + StringUtils.join(platformsNotOk.toArray(), ",");
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
