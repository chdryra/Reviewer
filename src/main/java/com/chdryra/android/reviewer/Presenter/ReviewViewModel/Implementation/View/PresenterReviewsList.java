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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutable;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterReviewsList implements
        AlertListener,
        NewReviewListener,
        ReviewNodeMutable.NodeObserver {

    private ApplicationInstance mApp;
    private ReviewNode mNode;
    private ReviewView<GvReview> mReviewView;
    private GridItemFeedScreen mGridItem;

    public PresenterReviewsList(ApplicationInstance app,
                                ReviewNode node,
                                Actions actions) {
        mNode = node;
        mNode.registerNodeObserver(this);

        mGridItem = actions.getGridItemAction();

        mApp = app;
        mReviewView = mApp.getLaunchableFactory().newReviewsListScreen(mNode,
                mApp.getReviewViewAdapterFactory(), actions);
    }

    protected ApplicationInstance getApp() {
        return mApp;
    }

    protected ReviewNode getNode() {
        return mNode;
    }

    public ReviewView<GvReview> getView() {
        return mReviewView;
    }

    protected void detach() {
        mNode.unregisterNodeObserver(this);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mGridItem.onAlertPositive(requestCode, args);
    }

    @Override
    public void onNodeChanged() {
        notifyReviewView();
    }

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mGridItem.onNewReviewUsingTemplate(template);
    }

    private void notifyReviewView() {
        if (mReviewView != null) mReviewView.onDataChanged();
    }

    /**
     * Created by: Rizwan Choudrey
     * On: 03/06/2016
     * Email: rizwan.choudrey@gmail.com
     */
    public static class Actions extends ReviewViewActions<GvReview> {
        public Actions(SubjectAction<GvReview> subjectAction,
                       RatingBarAction<GvReview> ratingBarAction,
                       BannerButtonAction<GvReview> bannerButtonAction,
                       GridItemFeedScreen gridItemAction,
                       MenuAction<GvReview> menuAction) {
            super(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction);
        }

        @Override
        public GridItemFeedScreen getGridItemAction() {
            return (GridItemFeedScreen) super.getGridItemAction();
        }
    }
}
