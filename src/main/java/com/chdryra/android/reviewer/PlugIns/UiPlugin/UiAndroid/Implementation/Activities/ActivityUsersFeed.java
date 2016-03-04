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
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterUsersFeed;
import com.chdryra.android.reviewer.Social.Implementation.PublishingAction;

import java.util.ArrayList;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityUsersFeed extends ActivityReviewView implements
        DialogAlertFragment.DialogAlertListener,
        DeleteRequestListener,
        NewReviewListener,
        PresenterUsersFeed.ReviewUploadedListener {

    private PresenterUsersFeed mPresenter;
    private ApplicationInstance mApp;

    @Override
    protected void onStart() {
        super.onStart();
        uploadNewReviewIfNecessary();
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
    public void onAlertNegative(int requestCode, Bundle args) {
        mPresenter.onAlertNegative(requestCode, args);
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mPresenter.onAlertPositive(requestCode, args);
    }

    @Override
    public void onDeleteRequested(ReviewId reviewId) {
        mPresenter.deleteFromUsersFeed(reviewId);
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mPresenter.onNewReviewUsingTemplate(template);
    }

    @Override
    public void onReviewUploaded(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void uploadNewReviewIfNecessary() {
        Intent intent = getIntent();
        String reviewId = intent.getStringExtra(PublishingAction.PUBLISHED);
        ArrayList<String> platforms = intent.getStringArrayListExtra(PublishingAction.PLATFORMS);
        if (reviewId != null && platforms != null && platforms.size() > 0) {
            mPresenter.upload(reviewId, platforms);
        }
    }

}
