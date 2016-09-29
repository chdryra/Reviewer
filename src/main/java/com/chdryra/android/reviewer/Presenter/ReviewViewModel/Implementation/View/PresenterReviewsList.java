/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvNode;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewsList implements ReviewNode.NodeObserver {

    private final ApplicationInstance mApp;
    private final ReviewsListView mReviewView;
    private final ReviewNode mNode;

    PresenterReviewsList(ApplicationInstance app, ReviewsListView reviewView) {
        mApp = app;
        mReviewView = reviewView;

        mNode = mReviewView.getNode();
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
    public void onTreeChanged() {
        notifyTreeChanged();
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
