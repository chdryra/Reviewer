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
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryError;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryMutableCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryObserver;
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
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Implementation.ReviewUploaderError;
import com.chdryra.android.reviewer.Social.Implementation.SocialPublishingError;
import com.chdryra.android.reviewer.Social.Interfaces.BackendReviewUploader;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewUploaderListener;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublishingListener;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

import org.apache.commons.lang3.StringUtils;

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
        ReviewsRepositoryObserver,
        SocialPublishingListener,
        ReviewUploaderListener,
        RepositoryCallback{

    private ApplicationInstance mApp;
    private ReviewNodeComponent mFeedNode;
    private FactoryReviews mReviewsFactory;
    private ReviewView<GvReviewOverview> mReviewView;
    private Actions mActions;
    private GridItemFeedScreen mGridItem;
    private SocialPlatformsPublisher mSocialUploader;
    private BackendReviewUploader mBackendReviewUploader;
    private ReviewUploadedListener mUploadedListener;

    public interface ReviewUploadedListener {
        void onReviewUploadedToSocialPlatforms(String message);

        void onReviewUploadedToBackend(String message);

        void onReviewDeletedFromBackend(String message);
    }

    private PresenterUsersFeed(ApplicationInstance app,
                               String title,
                               Collection<Review> feedReviews,
                               ReviewUploadedListener uploadedListener,
                               Actions actions) {
        mApp = app;
        mActions = actions;

        mReviewsFactory = mApp.getReviewsFactory();

        mFeedNode = mReviewsFactory.createMetaReviewMutable(feedReviews, title);
        mGridItem = (GridItemFeedScreen) mActions.getGridItemAction();
        mReviewView = mApp.getLaunchableFactory().newReviewsListScreen(mFeedNode,
                mApp.getReviewViewAdapterFactory(), mActions);

        mSocialUploader = app.newSocialPublisher();
        mSocialUploader.registerListener(this);

        mBackendReviewUploader = app.newBackendUploader();
        mBackendReviewUploader.registerListener(this);

        mUploadedListener = uploadedListener;
    }

    public ReviewView<GvReviewOverview> getView() {
        return mReviewView;
    }

    @Override
    public void onFetched(@Nullable Review review, RepositoryError error) {

    }

    @Override
    public void onCollectionFetched(Collection<Review> reviews, RepositoryError error) {

    }

    public void deleteFromUsersFeed(final ReviewId id) {
        mApp.deleteFromUsersFeed(id, new RepositoryMutableCallback() {
            @Override
            public void onAdded(Review review, RepositoryError error) {

            }

            @Override
            public void onRemoved(ReviewId reviewId, RepositoryError error) {
                mBackendReviewUploader.deleteReview(id);
            }
        });
    }

    public void publish(String reviewId, ArrayList<String> platforms) {
        mBackendReviewUploader.uploadReview(new DatumReviewId(reviewId));
        if(platforms != null && platforms.size() > 0) {
            mSocialUploader.publishToSocialPlatforms(new DatumReviewId(reviewId), platforms);
        }
    }

    public void detach() {
        mSocialUploader.unregisterListener(this);
        mBackendReviewUploader.unregisterListener(this);
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

    @Override
    public void onNewReviewUsingTemplate(ReviewId template) {
        mGridItem.onNewReviewUsingTemplate(template);
    }

    @Override
    public void onUploadStatus(double percentage, PublishResults justUploaded) {

    }

    @Override
    public void onUploadCompleted(Collection<PublishResults> publishedOk,
                                  Collection<PublishResults> publishedNotOk,
                                  SocialPublishingError error) {
        int numFollowers = 0;
        ArrayList<String> platformsOk = new ArrayList<>();
        for (PublishResults result : publishedOk) {
            platformsOk.add(result.getPublisherName());
            numFollowers += result.getFollowers();
        }

        ArrayList<String> platformsNotOk = new ArrayList<>();
        for (PublishResults result : publishedNotOk) {
            platformsNotOk.add(result.getPublisherName());
        }

        String message = getReviewUploadedMessage(platformsOk,
                platformsNotOk, numFollowers, error);
        mUploadedListener.onReviewUploadedToSocialPlatforms(message);
    }

    @Override
    public void onReviewUploaded(ReviewId reviewId, ReviewUploaderError error) {
        String message = error.isError() ? "Error uploading: " + error.getMessage() : "Review uploaded";
        mUploadedListener.onReviewUploadedToBackend(message);
    }

    @Override
    public void onReviewDeleted(ReviewId reviewId, ReviewUploaderError error) {
        String message = error.isError() ? "Error deleting: " + error.getMessage() : "Review deleted";
        mUploadedListener.onReviewDeletedFromBackend(message);
    }

    private String getReviewUploadedMessage(ArrayList<String> platformsOk,
                                            ArrayList<String> platformsNotOk,
                                            int numFollowers,
                                            SocialPublishingError error) {
        String message = "";

        if (platformsOk.size() > 0) {
            String num = String.valueOf(numFollowers);
            boolean fb = platformsOk.contains(PlatformFacebook.NAME);
            String plus = fb ? "+ " : "";
            String followers = numFollowers == 1 && !fb ? " follower" : " followers";
            String followersString = num + plus + followers;

            message = "Published to " + followersString + " on " +
                    StringUtils.join(platformsOk.toArray(), ", ");
        }

        String notOkMessage = "";
        if (platformsNotOk.size() > 0) {
            notOkMessage = "Problems publishing to " + StringUtils.join(platformsNotOk.toArray(),
                    ",");
            if (platformsOk.size() > 0) message += "\n" + notOkMessage;
        }

        if(error.isError()) {
            message += "\nError: " + error.getMessage();
        }
        return message;
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

    public static class Builder implements RepositoryCallback{
        private PresenterUsersFeed.ReviewUploadedListener mListener;
        private ApplicationInstance mApp;
        private BuildCallback mCallback;

        public interface BuildCallback {
            void onBuildFinished(PresenterUsersFeed presenter, RepositoryError error);
        }

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public Builder setReviewUploadedListener(PresenterUsersFeed.ReviewUploadedListener
                                                         listener) {
            mListener = listener;
            return this;
        }

        public void build(BuildCallback callback) {
            mCallback = callback;
            mApp.getUsersFeed().getReviews(this);
        }

        @Override
        public void onFetched(@Nullable Review review, RepositoryError error) {

        }

        @Override
        public void onCollectionFetched(Collection<Review> reviews, RepositoryError error) {
            String title = mApp.getUsersFeed().getAuthor() + "'s feed";
            PresenterUsersFeed presenter = new PresenterUsersFeed(mApp, title, reviews, mListener,getActions());
            mCallback.onBuildFinished(presenter, error);
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