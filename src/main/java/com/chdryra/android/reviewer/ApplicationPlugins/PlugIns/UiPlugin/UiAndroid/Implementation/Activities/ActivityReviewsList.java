/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;



import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.AndroidApp.AndroidAppInstance;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .DeleteRequestListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;

/**
 * UI Activity holding published reviews feed.
 */
public class ActivityReviewsList extends ActivityReviewView implements NewReviewListener,
        AlertListener,
        DeleteRequestListener {
    private static final String TAG = TagKeyGenerator.getTag(ActivityReviewsList.class);

    private PresenterReviewsList mPresenter;

    @Override
    protected ReviewView createReviewView() {
        mPresenter = newPresenter();
        return mPresenter.getView();
    }

    PresenterReviewsList newPresenter() {
        ReviewsListView view = (ReviewsListView) super.createReviewView();
        return new PresenterReviewsList.Builder().build(AndroidAppInstance.getInstance(this), view);
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    protected void onResume() {
        AndroidAppInstance.getInstance(this).discardReviewBuilderAdapter();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mPresenter.onNewReviewUsingTemplate(template);
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
}
