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
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryError;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .GooglePlacesApi.CallBackSignaler;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .PresenterUsersFeed;
import com.chdryra.android.reviewer.Social.Implementation.PublishingAction;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityUsersFeed extends ActivityReviewView implements
        DialogAlertFragment.DialogAlertListener,
        DeleteRequestListener,
        NewReviewListener,
        PresenterUsersFeed.ReviewUploadedListener {

    private static final int TIMEOUT = 100;

    private ApplicationInstance mApp;
    private PresenterUsersFeed mPresenter;
    private CallBackSignaler mSignaler;

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

        //mSignaler = new CallBackSignaler(TIMEOUT);
        Firebase ref = new Firebase("https://docs-examples.firebaseio.com/web/saving-data/fireblog/posts");
        // Attach an listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        buildPresenterAsync();

        mSignaler.waitForSignal();
        if (mSignaler.timedOut()) {
            //TODO deal with this more elegantly
            throw new RuntimeException("ActivityUsersFeed timed out!");
        } else {
            return mPresenter.getView();
        }
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
        mPresenter.deleteFromUsersFeed(reviewId);
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mPresenter.onNewReviewUsingTemplate(template);
    }

    @Override
    public void onReviewUploadedToSocialPlatforms(String message) {
        makeToast(message);
    }

    @Override
    public void onReviewUploadedToBackend(String message) {
        makeToast(message);
    }

    @Override
    public void onReviewDeletedFromBackend(String message) {
        makeToast(message);
    }

    private void buildPresenterAsync() {
        mApp = ApplicationInstance.getInstance(this);
        mSignaler = new CallBackSignaler(TIMEOUT);
        new PresenterUsersFeed.Builder(mApp)
                .setReviewUploadedListener(this)
                .build(new PresenterUsersFeed.Builder.BuildCallback() {
                    @Override
                    public void onBuildFinished(PresenterUsersFeed presenter, RepositoryError
                            error) {
                        mPresenter = presenter;
                        mSignaler.signal();
                    }
                });
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void publishNewReviewIfNecessary() {
        Intent intent = getIntent();
        String reviewId = intent.getStringExtra(PublishingAction.PUBLISHED);
        if (reviewId != null) {
            ArrayList<String> platforms = intent.getStringArrayListExtra(PublishingAction
                    .PLATFORMS);
            mPresenter.publish(reviewId, platforms);
            intent.removeExtra(PublishingAction.PUBLISHED);
        }
    }
}
