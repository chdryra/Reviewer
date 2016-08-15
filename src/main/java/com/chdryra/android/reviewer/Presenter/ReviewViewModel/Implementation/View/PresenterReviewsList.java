/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemReviewsList;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewsList implements AlertListener, NewReviewListener,
        ReviewNode.NodeObserver {

    private ApplicationInstance mApp;
    private ReviewsListView mReviewView;
    private ReviewNode mNode;
    private GridItemReviewsList mGridItem;

    protected PresenterReviewsList(ApplicationInstance app, ReviewsListView reviewView) {
        mApp = app;
        mReviewView = reviewView;

        mNode = mReviewView.getNode();
        mNode.registerObserver(this);

        mGridItem = (GridItemReviewsList) mReviewView.getActions().getGridItemAction();
    }

    protected ApplicationInstance getApp() {
        return mApp;
    }

    protected ReviewNode getNode() {
        return mNode;
    }

    public ReviewView<GvNode> getView() {
        return mReviewView;
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
        notifyListChanged();
    }

    @Override
    public void onChildRemoved(ReviewNode child) {
        notifyListChanged();
    }

    @Override
    public void onNodeChanged() {
        if (mReviewView != null) mReviewView.updateAll();
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mGridItem.onNewReviewUsingTemplate(template);
    }

    private void notifyListChanged() {
        if (mReviewView != null) mReviewView.onDataChanged();
    }

    public static class Builder {
        public PresenterReviewsList build(ApplicationInstance app, ReviewsListView view) {
            return new PresenterReviewsList(app, view);
        }
    }
}
