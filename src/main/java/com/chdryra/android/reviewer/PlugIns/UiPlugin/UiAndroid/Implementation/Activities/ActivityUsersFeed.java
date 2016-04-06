/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.os.Bundle;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.Dialogs.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationLaunch;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterUsersFeed;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

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
        PresenterUsersFeed.PresenterListener {

    private ApplicationInstance mApp;
    private PresenterUsersFeed mPresenter;

    @Override
    protected ReviewView createReviewView() {
        ApplicationLaunch.intitialiseLaunchIfNecessary(this, ApplicationLaunch.LaunchState.TEST);

        mApp = ApplicationInstance.getInstance(this);
        mPresenter = new PresenterUsersFeed.Builder(mApp, this).build();

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
    public void onDeletedFromBackend(ReviewId reviewId, CallbackMessage result) {
        makeToast(result.getMessage());
    }

    @Override
    public void onUploadFailed(ReviewId id, CallbackMessage result) {
        makeToast(result.getMessage());
    }

    @Override
    public void onUploadCompleted(ReviewId id, CallbackMessage result) {
        makeToast(result.getMessage());
    }

    @Override
    public void onPublishingFailed(ReviewId id, Collection<String> platforms, CallbackMessage
            result) {
        makeToast(result.getMessage());
    }

    @Override
    public void onPublishingCompleted(ReviewId id, Collection<PublishResults> platformsOk, Collection<PublishResults> platformsNotOk, CallbackMessage message) {
        int numFollowers = 0;
        ArrayList<String> ok = new ArrayList<>();
        for (PublishResults result : platformsOk) {
            ok.add(result.getPublisherName());
            numFollowers += result.getFollowers();
        }

        ArrayList<String> notOk = new ArrayList<>();
        for (PublishResults result : platformsNotOk) {
            notOk.add(result.getPublisherName());
        }

        makeToast(getPublishedMessage(ok, notOk, numFollowers, message));
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private String getPublishedMessage(ArrayList<String> platformsOk,
                                            ArrayList<String> platformsNotOk,
                                            int numFollowers,
                                            CallbackMessage result) {
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
