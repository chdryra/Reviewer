/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.reviewer.Application.Implementation.ReviewPacker;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.Authentication.Interfaces.SocialProfile;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.DataValue;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataParcelable;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.PublishAction;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewEditor;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewLauncher;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSocialPlatform;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.View.Configs.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncherAndroid;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PresenterContextBasic implements PresenterContext {
    private final ModelContext mModelContext;
    private final ViewContext mViewContext;
    private final SocialContext mSocialContext;
    private final NetworkContext mNetworkContext;
    private final PersistenceContext mPersistenceContext;
    private final ReviewPublisher mPublisher;

    private FactoryGvData mFactoryGvData;
    private FactoryReviewView mFactoryReviewView;
    private FactoryReviewLauncher mFactoryReviewLauncher;
    private ReviewEditor<? extends GvDataList<? extends GvDataParcelable>> mReviewEditor;

    protected PresenterContextBasic(ModelContext modelContext,
                                    ViewContext viewContext,
                                    SocialContext socialContext,
                                    NetworkContext networkContext,
                                    PersistenceContext persistenceContext) {
        mModelContext = modelContext;
        mViewContext = viewContext;
        mSocialContext = socialContext;
        mPersistenceContext = persistenceContext;
        mNetworkContext = networkContext;

        mPublisher = mNetworkContext.getPublisherFactory().newPublisher(mPersistenceContext
                .getLocalRepository());
    }

    protected void setFactoryReviewView(FactoryReviewView factoryReviewView) {
        mFactoryReviewView = factoryReviewView;
    }

    protected void setFactoryGvData(FactoryGvData factoryGvData) {
        mFactoryGvData = factoryGvData;
    }

    protected void setFactoryReviewLauncher(FactoryReviewLauncher factoryReviewLauncher) {
        mFactoryReviewLauncher = factoryReviewLauncher;
    }

    protected FactoryGvData getGvDataFactory() {
        return mFactoryGvData;
    }

    @Override
    public FactoryReviews getReviewsFactory() {
        return mModelContext.getReviewsFactory();
    }

    @Override
    public SocialPlatformList getSocialPlatformList() {
        return mSocialContext.getSocialPlatforms();
    }

    @Override
    public UiConfig getUiConfig() {
        return mViewContext.getUiConfig();
    }

    @Override
    public UiLauncherAndroid newUiLauncher(ReviewPacker packer) {
        return mViewContext.getLauncherFactory().newLauncher(this, mFactoryReviewView, getMasterRepository(), packer, getUiConfig().getBuildReview());
    }

    @Override
    public ReviewEditor<?> newReviewEditor(@Nullable Review template, LocationClient client) {
        mReviewEditor = mFactoryReviewView.newEditor(template, client);
        return mReviewEditor;
    }

    @Override
    public void discardReviewEditor() {
        mReviewEditor = null;
    }

    @Override
    public ReviewEditor<? extends GvDataList<? extends GvDataParcelable>> getReviewEditor() {
        return mReviewEditor;
    }

    @Override
    public Review executeReviewEditor() {
        Review published = mReviewEditor.buildReview();
        discardReviewEditor();

        return published;
    }

    @Override
    public ReviewView<?> newPublishScreen(PlatformAuthoriser authoriser, PublishAction.PublishCallback callback) {
        ReviewView<?> editor = mReviewEditor != null ?
                mReviewEditor : mFactoryReviewView.newNullView(GvSocialPlatform.TYPE);
        return mFactoryReviewView.newPublishView(editor.getAdapter(), getSocialPlatformList(), authoriser, mPublisher,
                callback);
    }

    @Override
    public void getReview(ReviewId id, final RepositoryCallback callback) {
        getMasterRepository().getReference(id, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                ReviewReference reference = result.getReference();
                if(result.isReference() && reference != null) {
                    reference.dereference(new DataReference.DereferenceCallback<Review>() {
                        @Override
                        public void onDereferenced(DataValue<Review> review) {
                            callback.onRepositoryCallback(new RepositoryResult(review.getData(), review.getMessage()));
                        }
                    });
                }
            }
        });
    }

    @Override
    public TagsManager getTagsManager() {
        return mModelContext.getTagsManager();
    }

    @Override
    public ReviewPublisher getReviewPublisher() {
        return mPublisher;
    }

    @Override
    public ReviewsSource getMasterRepository() {
        return mPersistenceContext.getReviewsSource();
    }

    @Override
    public ReferencesRepository newFeed(SocialProfile profile) {
        return mPersistenceContext.getRepoFactory().newFeed(profile.getAuthorId(),
                profile.getFollowing(), getMasterRepository());
    }

    @Override
    public AccountsManager getAccountsManager() {
        return mPersistenceContext.getAccountsManager();
    }

    @Override
    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mNetworkContext.getDeleterFactory().newDeleter(id, getTagsManager());
    }

    @Override
    public ReviewsListView newFeedView(ReviewNode reviewNode) {
        return mFactoryReviewView.newFeedView(reviewNode);
    }
}
