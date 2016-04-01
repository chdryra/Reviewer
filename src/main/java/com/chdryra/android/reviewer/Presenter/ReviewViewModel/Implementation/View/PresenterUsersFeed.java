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
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.DialogAlertFragment;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutable;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutableAsync;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryMessage;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryMutableCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.NetworkServices.Backend.BackendReviewUploader;
import com.chdryra.android.reviewer.NetworkServices.Backend.ReviewUploaderMessage;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.SocialPublishingMessage;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.ReviewUploaderListener;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPublishingListener;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.BannerButtonAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.MenuAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.RatingBarAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.Actions.SubjectAction;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.BannerButtonActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.GridItemFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.MenuFeedScreen;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.NewReviewListener;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.RatingBarExpandGrid;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.ReviewViewActions;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Actions.SubjectActionNone;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvReviewOverview;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 18/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterUsersFeed implements
        DialogAlertFragment.DialogAlertListener,
        NewReviewListener,
        SocialPublishingListener,
        ReviewUploaderListener,
        ReviewNodeMutable.NodeObserver {

    private ApplicationInstance mApp;
    private ReviewNodeMutableAsync mFeedNode;
    private ReviewView<GvReviewOverview> mReviewView;
    private GridItemFeedScreen mGridItem;
    private SocialPlatformsPublisher mSocialUploader;
    private BackendReviewUploader mBackendReviewUploader;
    private ReviewUploadedListener mListener;

    public interface ReviewUploadedListener {
        void onReviewPublishedToSocialPlatforms(Collection<PublishResults> publishedOk,
                                                Collection<PublishResults> publishedNotOk,
                                                SocialPublishingMessage result);

        void onReviewUploadedToBackend(ReviewId id, ReviewUploaderMessage result);

        void onReviewDeletedFromBackend(ReviewId id, ReviewUploaderMessage result);
    }

    private PresenterUsersFeed(ApplicationInstance app,
                               ReviewNodeMutableAsync feedNode,
                               Actions actions,
                               @Nullable ReviewUploadedListener listener) {
        mApp = app;
        mFeedNode = feedNode;
        mFeedNode.registerNodeObserver(this);

        mGridItem = (GridItemFeedScreen) actions.getGridItemAction();
        mReviewView = mApp.getLaunchableFactory().newReviewsListScreen(mFeedNode,
                mApp.getReviewViewAdapterFactory(), actions);

        mSocialUploader = app.newSocialPublisher();
        mSocialUploader.registerListener(this);
        mBackendReviewUploader = app.newBackendUploader();
        mBackendReviewUploader.registerListener(this);

        mListener = listener;
    }

    public ReviewView<GvReviewOverview> getView() {
        return mReviewView;
    }

    public void deleteFromUsersFeed(final ReviewId id) {
        mApp.deleteFromUsersFeed(id, new RepositoryMutableCallback() {
            @Override
            public void onAdded(Review review, RepositoryMessage result) {

            }

            @Override
            public void onRemoved(ReviewId reviewId, RepositoryMessage result) {
                mBackendReviewUploader.deleteReview(id);
            }
        });
    }

    public void publish(String reviewId, ArrayList<String> platforms) {
        DatumReviewId id = new DatumReviewId(reviewId);
        mBackendReviewUploader.uploadReview(id);
        if (platforms != null && platforms.size() > 0) {
            mSocialUploader.publishToSocialPlatforms(id, platforms);
        }
    }

    public void detach() {
        mSocialUploader.unregisterListener(this);
        mBackendReviewUploader.unregisterListener(this);
        mFeedNode.unregisterNodeObserver(this);
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
    public void onPublishStatus(double percentage, PublishResults justUploaded) {

    }

    @Override
    public void onPublishCompleted(Collection<PublishResults> publishedOk,
                                   Collection<PublishResults> publishedNotOk,
                                   SocialPublishingMessage result) {
        if (mListener != null) {
            mListener.onReviewPublishedToSocialPlatforms(publishedOk, publishedNotOk, result);
        }
    }

    @Override
    public void onReviewUploaded(ReviewId reviewId, ReviewUploaderMessage result) {
        if (mListener != null) {
            mListener.onReviewUploadedToBackend(reviewId, result);
        }
    }

    @Override
    public void onReviewDeleted(ReviewId reviewId, ReviewUploaderMessage result) {
        mApp.getTagsManager().clearTags(reviewId.toString());
        if (mListener != null) {
            mListener.onReviewDeletedFromBackend(reviewId, result);
        }
    }

    private void notifyReviewView() {
        if (mReviewView != null) mReviewView.onDataChanged();
    }

    public static class Actions extends ReviewViewActions<GvReviewOverview> {
        public Actions(SubjectAction<GvReviewOverview> subjectAction, RatingBarAction
                <GvReviewOverview> ratingBarAction, BannerButtonAction<GvReviewOverview>
                               bannerButtonAction, GridItemFeedScreen gridItemAction,
                       MenuAction<GvReviewOverview> menuAction) {
            super(subjectAction, ratingBarAction, bannerButtonAction, gridItemAction, menuAction);
        }
    }

    public static class Builder {
        private PresenterUsersFeed.ReviewUploadedListener mListener;
        private ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public Builder setReviewUploadedListener(PresenterUsersFeed.ReviewUploadedListener
                                                         listener) {
            mListener = listener;
            return this;
        }

        public PresenterUsersFeed build() {
            ReviewsFeed usersFeed = mApp.getUsersFeed();
            String title = usersFeed.getAuthor().getName() + "'s feed";

            ReviewTreeRepoCallback callback
                    = new ReviewTreeRepoCallback(new ArrayList<Review>(),
                    mApp.getReviewsFactory(), title);
            usersFeed.getReviews(callback);

            return new PresenterUsersFeed(mApp, callback, getActions(), mListener);
        }

        @NonNull
        private PresenterUsersFeed.Actions getActions() {
            ConfigUi configDataUi = mApp.getConfigDataUi();
            FactoryReviewViewLaunchable launchableFactory = mApp.getLaunchableFactory();
            LaunchableUiLauncher uiLauncher = mApp.getUiLauncher();
            LaunchableUi reviewBuildUi = configDataUi.getBuildReviewConfig().getLaunchable();

            GridItemFeedScreen gi = new GridItemFeedScreen(launchableFactory, uiLauncher,
                    configDataUi.getShareEditConfig().getLaunchable(), reviewBuildUi);
            SubjectAction<GvReviewOverview> sa = new SubjectActionNone<>();
            RatingBarAction<GvReviewOverview> rb
                    = new RatingBarExpandGrid<>(launchableFactory, uiLauncher);
            BannerButtonAction<GvReviewOverview> bba = new BannerButtonActionNone<>();
            MenuFeedScreen ma = new MenuFeedScreen(uiLauncher, reviewBuildUi);

            return new PresenterUsersFeed.Actions(sa, rb, bba, gi, ma);
        }
    }
}
