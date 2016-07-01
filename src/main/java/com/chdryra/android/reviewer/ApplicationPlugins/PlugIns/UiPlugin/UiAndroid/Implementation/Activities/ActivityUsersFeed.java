/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterUsersFeed;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;

import java.util.Collection;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityUsersFeed extends ActivityReviewsList implements
        AlertListener,
        DeleteRequestListener,
        PresenterUsersFeed.PresenterListener {

    private PresenterUsersFeed mPresenter;

    @Override
    protected PresenterReviewsList newPresenter() {
        AndroidAppInstance app = AndroidAppInstance.getInstance(this);
        mPresenter = new PresenterUsersFeed.Builder(app).build(this);
        return mPresenter;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
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
    public void onReviewDeleted(ReviewId reviewId, CallbackMessage result) {
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
    public void onPublishingFailed(ReviewId reviewId, Collection<String> platforms, CallbackMessage
            result) {
        makeToast(result.getMessage());
    }

    @Override
    public void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults justUploaded) {

    }

    @Override
    public void onPublishingCompleted(ReviewId reviewId,
                                      Collection<PublishResults> platformsOk,
                                      Collection<PublishResults> platformsNotOk,
                                      CallbackMessage message) {
        makeToast(mPresenter.getPublishedMessage(platformsOk, platformsNotOk, message));
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
