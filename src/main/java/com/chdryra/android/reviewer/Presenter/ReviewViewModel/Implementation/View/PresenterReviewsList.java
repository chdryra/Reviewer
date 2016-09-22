/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewsList implements AlertListener, NewReviewListener,
        ReviewNode.NodeObserver {

    private final ApplicationInstance mApp;
    private final ReviewsListView mReviewView;
    private final ReviewNode mNode;
    private final GridItemReviewsList mGridItem;

    PresenterReviewsList(ApplicationInstance app, ReviewsListView reviewView) {
        mApp = app;
        mReviewView = reviewView;

        mNode = mReviewView.getNode();

        mGridItem = (GridItemReviewsList) mReviewView.getActions().getGridItemAction();
    }

    ApplicationInstance getApp() {
        return mApp;
    }

    ReviewNode getNode() {
        return mNode;
    }

    public ReviewView<GvNode> getView() {
        return mReviewView;
    }

    public void attach() {
        mNode.registerObserver(this);
    }

    public void detach() {
        mNode.unregisterObserver(this);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mGridItem.onAlertPositive(requestCode, args);
    }

    @Override
    public void onChildAdded(ReviewNode child) {
        notifyTreeChanged();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        notifyTreeChanged();
    }

    @Override
    public void onNodeChanged() {
        if (mReviewView != null) mReviewView.notifyDataObservers();
    }

    @Override
    public void onDescendantsChanged() {
        notifyTreeChanged();
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mApp.getCurrentScreen().showToast(Strings.Toasts.COPYING);
        mGridItem.onNewReviewUsingTemplate(template);
    }

    public void deleteReview(final ReviewId id) {
        makeToast(Strings.Toasts.DELETING);
        ReviewDeleter deleter = getApp().newReviewDeleter(id);
        deleter.deleteReview(new ReviewDeleter.ReviewDeleterCallback() {
            @Override
            public void onReviewDeleted(ReviewId reviewId, CallbackMessage result) {
                getApp().getTagsManager().clearTags(reviewId.toString());
                makeToast(result.getMessage());
            }
        });
    }

    private void makeToast(String message) {
        getApp().getCurrentScreen().showToast(message);
    }

    private void notifyTreeChanged() {
        if (mReviewView != null) mReviewView.updateAll();
    }

    public static class Builder {
        public PresenterReviewsList build(ApplicationInstance app, ReviewsListView view) {
            return new PresenterReviewsList(app, view);
        }
    }
}
