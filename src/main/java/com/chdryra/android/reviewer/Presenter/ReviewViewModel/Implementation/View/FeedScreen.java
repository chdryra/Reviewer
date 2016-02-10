/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FeedScreen implements
        DialogAlertFragment.DialogAlertListener,
        ReviewsRepositoryObserver {

    private ReviewNodeComponent mFeedNode;
    private FactoryReviews mReviewsFactory;
    private ReviewView<GvReviewOverview> mReviewView;
    private GridItemFeedScreen mGridItem;

    public FeedScreen(ReviewsFeed feed, String title, FactoryReviews reviewsFactory) {
        mReviewsFactory = reviewsFactory;
        mFeedNode = mReviewsFactory.createMetaReviewMutable(feed.getReviews(), title);
        feed.registerObserver(this);
    }

    public ReviewView<GvReviewOverview> createView(FactoryReviewViewLaunchable launchableFactory,
                                                   FactoryReviewViewAdapter adapterFactory,
                                                   Actions actions) {
        mGridItem = (GridItemFeedScreen) actions.getGridItemAction();
        mReviewView = launchableFactory.newReviewsListScreen(mFeedNode, adapterFactory, actions);

        return mReviewView;
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mGridItem.onAlertPositive(requestCode, args);
    }

    @Override
    public void onReviewAdded(Review review) {
        addReviewToNode(review);
        if (mReviewView != null) mReviewView.onGridDataChanged();
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        removeReviewFromNode(reviewId);
        if (mReviewView != null) mReviewView.onGridDataChanged();
    }

    private void addReviewToNode(Review review) {
        mFeedNode.addChild(mReviewsFactory.createReviewNodeComponent(review, false));
    }

    private void removeReviewFromNode(ReviewId reviewId) {
        mFeedNode.removeChild(reviewId);
    }

    public static class Actions extends ReviewViewActions<GvReviewOverview> {
        public Actions(SubjectAction<GvReviewOverview> subjectAction, RatingBarAction
                <GvReviewOverview> ratingBarAction, BannerButtonAction<GvReviewOverview>
                               bannerButtonAction, GridItemFeedScreen gridItemAction,
                       MenuAction<GvReviewOverview> menuAction) {
            super(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction);
        }
    }
}
