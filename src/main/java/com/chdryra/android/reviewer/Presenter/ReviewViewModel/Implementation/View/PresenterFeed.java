/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutable;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .GridItemFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions
        .SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReview;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterFeed implements
        AlertListener,
        NewReviewListener,
        ReviewNodeMutable.NodeObserver {

    private ApplicationInstance mApp;
    private ReviewTreeLive mFeedNode;
    private ReviewView<GvReview> mReviewView;
    private GridItemFeedScreen mGridItem;
    private ReviewDeleter mDeleter;

    private PresenterFeed(ApplicationInstance app,
                          ReviewTreeLive feedNode,
                          Actions actions) {
        mApp = app;
        mFeedNode = feedNode;
        mFeedNode.registerNodeObserver(this);

        mGridItem = (GridItemFeedScreen) actions.getGridItemAction();
        mReviewView = mApp.getLaunchableFactory().newReviewsListScreen(mFeedNode,
                mApp.getReviewViewAdapterFactory(), actions);
    }

    public ReviewView<GvReview> getView() {
        return mReviewView;
    }

    public void detach() {
        mFeedNode.unregisterNodeObserver(this);
        mFeedNode.detachFromRepo();
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

    public static class Builder {
        private ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public PresenterFeed build(DataAuthor author) {
            ReviewsFeed feed = mApp.getFeed(author);
            String title = author.getName() + "'s feed";
            ReviewTreeLive node = new ReviewTreeLive(feed, mApp.getReviewsFactory(), title);

            return new PresenterFeed(mApp, node, getActions());
        }

        @NonNull
        private PresenterFeed.Actions getActions() {
            FactoryReviewViewLaunchable launchableFactory = mApp.getLaunchableFactory();
            ConfigUi configUi = mApp.getConfigUi();
            LaunchableUi reviewBuildUi = configUi.getBuildReviewConfig().getLaunchable();

            GridItemFeedScreen gi = new GridItemFeedScreen(launchableFactory,
                    configUi.getShareEditConfig().getLaunchable(), reviewBuildUi);

            SubjectAction<GvReview> sa = new SubjectActionNone<>();

            RatingBarAction<GvReview> rb = new RatingBarExpandGrid<>(launchableFactory);

            BannerButtonAction<GvReview> bba = new BannerButtonActionNone<>();

            MenuFeedScreen ma = new MenuFeedScreen(reviewBuildUi);

            return new Actions(sa, rb, bba, gi, ma);
        }
    }

    private static class Actions extends ReviewViewActions<GvReview> {
        private Actions(SubjectAction<GvReview> subjectAction, RatingBarAction
                <GvReview> ratingBarAction, BannerButtonAction<GvReview>
                                bannerButtonAction, GridItemFeedScreen gridItemAction,
                        MenuAction<GvReview> menuAction) {
            super(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction);
        }
    }
}
