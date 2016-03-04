/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.FeedScreen;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.Social.Implementation.ReviewUploadService;
import com.chdryra.android.reviewer.Social.Implementation.UploadReviewServiceReceiver;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityFeed extends ActivityReviewView implements
        DialogAlertFragment.DialogAlertListener,
        DeleteRequestListener,
        NewReviewListener,
        UploadReviewServiceReceiver.UploadReviewServiceListener {

    private FeedScreen mScreen;
    private ApplicationInstance mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createUploadReviewServiceReceiver();
    }

    @Override
    protected ReviewView createReviewView() {
        ApplicationLaunch.intitialiseLaunchIfNecessary(this, ApplicationLaunch.LaunchState.TEST);

        mApp = ApplicationInstance.getInstance(this);

        uploadNewReviewIfNecessary();

        FactoryFeedScreen feedScreenBuilder = getScreenBuilder();
        feedScreenBuilder.buildScreen(mApp.getAuthorsFeed());
        mScreen = feedScreenBuilder.getFeedScreen();

        return feedScreenBuilder.getView();
    }

    @Override
    protected void onResume() {
        mApp.discardReviewBuilderAdapter();
        super.onResume();
    }

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
    public void onNewReviewUsingTemplate(ReviewId template) {
        mScreen.onNewReviewUsingTemplate(template);
    }

    @Override
    public void onStatusUpdate(double percentage, PublishResults justUploaded) {

    }

    @Override
    public void onUploadCompleted(Collection<PublishResults> publishedOk,
                                  Collection<PublishResults> publishedNotOk) {
        ArrayList<String> platformsOk = new ArrayList<>();
        ArrayList<String> platformsNotOk = new ArrayList<>();
        int numFollowers = 0;
        for (PublishResults result : publishedOk) {
            platformsOk.add(result.getPublisherName());
            numFollowers += result.getFollowers();
        }

        for (PublishResults result : publishedNotOk) {
            platformsNotOk.add(result.getPublisherName());
        }

        String message = makeMessage(platformsOk, platformsNotOk, numFollowers);
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

    private void createUploadReviewServiceReceiver() {
        IntentFilter statusUpdateFilter = new IntentFilter(ReviewUploadService.STATUS_UPDATE);
        IntentFilter uploadCompletedFilter = new IntentFilter(ReviewUploadService.UPLOAD_COMPLETED);

        UploadReviewServiceReceiver receiver = new UploadReviewServiceReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, statusUpdateFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, uploadCompletedFilter);
    }

    private void uploadNewReviewIfNecessary() {
        Intent intent = getIntent();
        String reviewId = intent.getStringExtra(PublishingAction.PUBLISHED);
        ArrayList<String> platforms = intent.getStringArrayListExtra(PublishingAction.PLATFORMS);
        if (reviewId != null && platforms != null && platforms.size() > 0) {
            startUploadReviewService(reviewId, platforms);
        }
    }

    private void startUploadReviewService(String reviewId, ArrayList<String> platforms) {
        Intent shareService = new Intent(this, ReviewUploadService.class);
        shareService.putExtra(PublishingAction.PUBLISHED, reviewId);
        shareService.putStringArrayListExtra(PublishingAction.PLATFORMS, platforms);
        startService(shareService);
    }

    private String makeMessage(ArrayList<String> platformsOk,
                               ArrayList<String> platformsNotOk,
                               int numFollowers) {
        String message = "";

        if (platformsOk.size() > 0) {
            String num = String.valueOf(numFollowers);
            boolean fb = platformsOk.contains(PlatformFacebook.NAME);
            String plus = fb ? "+ " : "";
            String followers = numFollowers == 1 && !fb ? " follower" : " followers";
            String followersString = num + plus + followers;

            message = "Published to " + followersString + " on " +
                    StringUtils.join(platformsOk.toArray(), ", ");
        }

        String notOkMessage = "";
        if (platformsNotOk.size() > 0) {
            notOkMessage = "Problems publishing to " + StringUtils.join(platformsNotOk.toArray(),
                    ",");
            if (platformsOk.size() > 0) message += "\n" + notOkMessage;
        }

        return message;
    }
}
