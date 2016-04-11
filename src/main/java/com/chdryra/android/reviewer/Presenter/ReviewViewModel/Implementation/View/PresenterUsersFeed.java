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

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.Dialogs.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutable;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutableAsync;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleterListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisherListener;
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
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterUsersFeed implements
        DialogAlertFragment.DialogAlertListener,
        NewReviewListener,
        ReviewPublisherListener,
        ReviewDeleterListener,
        ReviewNodeMutable.NodeObserver {

    private ApplicationInstance mApp;
    private ReviewNodeMutableAsync mFeedNode;
    private ReviewView<GvReviewOverview> mReviewView;
    private GridItemFeedScreen mGridItem;
    private PresenterListener mListener;
    private ReviewDeleter mDeleter;

    public interface PresenterListener extends ReviewPublisherListener, ReviewDeleterListener {
        @Override
        void onReviewDeleted(ReviewId reviewId, CallbackMessage result);

        @Override
        void onUploadFailed(ReviewId id, CallbackMessage result);

        @Override
        void onUploadCompleted(ReviewId id, CallbackMessage result);

        @Override
        void onPublishingFailed(ReviewId reviewId, Collection<String> platforms, CallbackMessage result);

        @Override
        void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults justUploaded);

        @Override
        void onPublishingCompleted(ReviewId reviewId, Collection<PublishResults> platformsOk,
                                   Collection<PublishResults> platformsNotOk, CallbackMessage
                                           result);
    }

    private PresenterUsersFeed(ApplicationInstance app,
                               ReviewNodeMutableAsync feedNode,
                               Actions actions,
                               PresenterListener listener) {
        mApp = app;
        mFeedNode = feedNode;
        mFeedNode.registerNodeObserver(this);

        mGridItem = (GridItemFeedScreen) actions.getGridItemAction();
        mReviewView = mApp.getLaunchableFactory().newReviewsListScreen(mFeedNode,
                mApp.getReviewViewAdapterFactory(), actions);

        mApp.getPublisher().registerListener(this);

        mListener = listener;
    }

    public ReviewView<GvReviewOverview> getView() {
        return mReviewView;
    }

    public void deleteReview(final ReviewId id) {
        mDeleter = mApp.newReviewDeleter(id);
        mDeleter.registerListener(this);
        mDeleter.deleteReview();
    }

    public void detach() {
        mApp.getPublisher().unregisterListener(this);
        mFeedNode.unregisterNodeObserver(this);
        if(mDeleter != null) mDeleter.unregisterListener(this);
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

    @Override
    public void onUploadFailed(ReviewId id, CallbackMessage result) {
        mListener.onUploadFailed(id, result);
    }

    @Override
    public void onUploadCompleted(ReviewId id, CallbackMessage result) {
        mListener.onUploadCompleted(id, result);
    }

    @Override
    public void onPublishingFailed(ReviewId reviewId, Collection<String> platforms, CallbackMessage
            result) {
        mListener.onPublishingFailed(reviewId, platforms, result);
    }

    @Override
    public void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults
            justUploaded) {
        mListener.onPublishingStatus(reviewId, percentage, justUploaded);
    }

    @Override
    public void onPublishingCompleted(ReviewId reviewId, Collection<PublishResults> platformsOk,
                                      Collection<PublishResults> platformsNotOk, CallbackMessage
                                                  result) {
        mListener.onPublishingCompleted(reviewId, platformsOk, platformsNotOk, result);
    }

    @Override
    public void onReviewDeleted(ReviewId reviewId, CallbackMessage result) {
        mApp.getTagsManager().clearTags(reviewId.toString());
        mListener.onReviewDeleted(reviewId, result);
        mDeleter.unregisterListener(this);
    }

    private void notifyReviewView() {
        if (mReviewView != null) mReviewView.onDataChanged();
    }

    public static class Builder {
        private PresenterUsersFeed.PresenterListener mListener;
        private ApplicationInstance mApp;

        public Builder(ApplicationInstance app, PresenterListener listener) {
            mApp = app;
            mListener = listener;
        }

        public PresenterUsersFeed build() {
            ReviewsFeed usersFeed = mApp.getUsersFeed();
            String title = usersFeed.getAuthor().getName() + "'s feed";
            ReviewTreeLive node = new ReviewTreeLive(usersFeed, mApp.getReviewsFactory(), title);

            return new PresenterUsersFeed(mApp, node, getActions(), mListener);
        }

        @NonNull
        private PresenterUsersFeed.Actions getActions() {
            FactoryReviewViewLaunchable launchableFactory = mApp.getLaunchableFactory();
            LaunchableUiLauncher uiLauncher = mApp.getUiLauncher();
            ConfigUi configDataUi = mApp.getConfigDataUi();
            LaunchableUi reviewBuildUi = configDataUi.getBuildReviewConfig().getLaunchable();

            GridItemFeedScreen gi = new GridItemFeedScreen(launchableFactory, uiLauncher,
                    configDataUi.getShareEditConfig().getLaunchable(), reviewBuildUi);

            SubjectAction<GvReviewOverview> sa = new SubjectActionNone<>();

            RatingBarAction<GvReviewOverview> rb
                    = new RatingBarExpandGrid<>(launchableFactory, uiLauncher);

            BannerButtonAction<GvReviewOverview> bba = new BannerButtonActionNone<>();

            MenuFeedScreen ma = new MenuFeedScreen(uiLauncher, reviewBuildUi);

            return new Actions(sa, rb, bba, gi, ma);
        }
    }

    private static class Actions extends ReviewViewActions<GvReviewOverview> {
        private Actions(SubjectAction<GvReviewOverview> subjectAction, RatingBarAction
                <GvReviewOverview> ratingBarAction, BannerButtonAction<GvReviewOverview>
                                bannerButtonAction, GridItemFeedScreen gridItemAction,
                        MenuAction<GvReviewOverview> menuAction) {
            super(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction);
        }
    }
}
