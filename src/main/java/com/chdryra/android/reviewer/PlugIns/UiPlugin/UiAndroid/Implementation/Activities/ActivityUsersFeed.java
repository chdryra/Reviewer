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
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.Backend.ReviewUploaderMessage;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterUsersFeed;

import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.SocialPublishingMessage;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityUsersFeed extends ActivityReviewView implements
        DialogAlertFragment.DialogAlertListener,
        DeleteRequestListener,
        NewReviewListener,
        PresenterUsersFeed.ReviewUploadedListener {

    private ApplicationInstance mApp;
    private PresenterUsersFeed mPresenter;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        publishNewReviewIfNecessary();
    }

    @Override
    protected ReviewView createReviewView() {
        ApplicationLaunch.intitialiseLaunchIfNecessary(this, ApplicationLaunch.LaunchState.TEST);

        mApp = ApplicationInstance.getInstance(this);
        mPresenter = new PresenterUsersFeed.Builder(mApp)
                .setReviewUploadedListener(this)
                .build();

        return mPresenter.getView();
    }

    @Override
    protected void onResume() {
        mApp.discardReviewBuilderAdapter();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mPresenter.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mPresenter.onAlertPositive(requestCode, args);
    }

    @Override
    public void onDeleteRequested(ReviewId reviewId) {
        mPresenter.deleteReview(reviewId);
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mPresenter.onNewReviewUsingTemplate(template);
    }

    @Override
    public void onReviewPublishedToSocialPlatforms(Collection<PublishResults> publishedOk,
                                                   Collection<PublishResults> publishedNotOk,
                                                   SocialPublishingMessage message) {
        int numFollowers = 0;
        ArrayList<String> platformsOk = new ArrayList<>();
        for (PublishResults result : publishedOk) {
            platformsOk.add(result.getPublisherName());
            numFollowers += result.getFollowers();
        }

        ArrayList<String> platformsNotOk = new ArrayList<>();
        for (PublishResults result : publishedNotOk) {
            platformsNotOk.add(result.getPublisherName());
        }

        makeToast(getPublishedMessage(platformsOk, platformsNotOk, numFollowers, message));
    }

    @Override
    public void onReviewUploadedToBackend(ReviewId id, ReviewUploaderMessage result) {
        makeToast(result.getMessage());
    }

    @Override
    public void onReviewDeletedFromBackend(ReviewId id, ReviewUploaderMessage result) {
        makeToast(result.getMessage());
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void publishNewReviewIfNecessary() {
        Intent intent = getIntent();
        String reviewId = intent.getStringExtra(PublishingAction.PUBLISHED);
        String error = intent.getStringExtra(PublishingAction.RepoError);
        if(error != null) {
            Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
        } else if (reviewId != null) {
            ArrayList<String> platforms
                    = intent.getStringArrayListExtra(PublishingAction.PLATFORMS);
            mPresenter.publish(reviewId, platforms);
            intent.removeExtra(PublishingAction.PUBLISHED);
        }
    }

    private String getPublishedMessage(ArrayList<String> platformsOk,
                                            ArrayList<String> platformsNotOk,
                                            int numFollowers,
                                            SocialPublishingMessage result) {
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

        if (result.isError()) {
            message += "\nError: " + result.getMessage();
        }
        return message;
    }
}
