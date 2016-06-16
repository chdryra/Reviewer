/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewView;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.ReviewsListView;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.FactoryUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PresenterContextBasic implements PresenterContext {
    private ModelContext mModelContext;
    private ViewContext mViewContext;
    private SocialContext mSocialContext;
    private NetworkContext mNetworkContext;
    private PersistenceContext mPersistenceContext;
    private ReviewPublisher mPublisher;

    private FactoryGvData mFactoryGvData;
    private FactoryReviewBuilderAdapter<?> mFactoryBuilderAdapter;
    private FactoryReviewViewAdapter mFactoryReviewViewAdapter;
    private FactoryReviewView mFactoryReviewView;
    private ReviewBuilderAdapter<?> mReviewBuilderAdapter;

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

    public void setFactoryReviewView(FactoryReviewView
                                             factoryReviewView) {
        mFactoryReviewView = factoryReviewView;
    }

    public void setFactoryGvData(FactoryGvData factoryGvData) {
        mFactoryGvData = factoryGvData;
    }


    public void setFactoryBuilderAdapter(FactoryReviewBuilderAdapter<?> factoryBuilderAdapter) {
        mFactoryBuilderAdapter = factoryBuilderAdapter;
    }

    public void setFactoryReviewViewAdapter(FactoryReviewViewAdapter factoryReviewViewAdapter) {
        mFactoryReviewViewAdapter = factoryReviewViewAdapter;
    }

    protected FactoryReviewView getFactoryReviewView() {
        return mFactoryReviewView;
    }

    @Override
    public FactoryGvData getGvDataFactory() {
        return mFactoryGvData;
    }

    @Override
    public FactoryReviewViewParams getReviewViewParamsFactory() {
        return mFactoryReviewView.getParamsFactory();
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
    public ConfigUi getConfigUi() {
        return mViewContext.getUiConfig();
    }

    @Override
    public FactoryUiLauncher getLauncherFactory() {
        return mViewContext.getLauncherFactory();
    }

    @Override
    public void asMetaReview(ReviewId reviewId, ReviewsSource.ReviewsSourceCallback callback) {
        mPersistenceContext.getReviewsSource().asMetaReview(reviewId, callback);
    }

    @Override
    public ReviewBuilderAdapter<?> newReviewBuilderAdapter(@Nullable Review template) {
        mReviewBuilderAdapter = mFactoryBuilderAdapter.newAdapter(template);
        return mReviewBuilderAdapter;
    }

    @Override
    public void discardReviewBuilderAdapter() {
        mReviewBuilderAdapter = null;
    }

    @Override
    public ReviewBuilderAdapter<?> getReviewBuilderAdapter() {
        return mReviewBuilderAdapter;
    }

    @Override
    public Review executeReviewBuilder() {
        Review published = mReviewBuilderAdapter.buildReview();
        discardReviewBuilderAdapter();

        return published;
    }

    @Override
    public void getReview(ReviewId id, ReviewsRepository.RepositoryCallback callback) {
        mPersistenceContext.getReviewsSource().getReview(id, callback);
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
    public ReviewsRepository getReviews(DataAuthor author) {
        return mPersistenceContext.getReviewsSource().getReviews(author);
    }

    @Override
    public ReviewsRepositoryMutable getBackendRepository() {
        return mPersistenceContext.getBackendRepository();
    }

    @Override
    public UsersManager getUsersManager() {
        return mPersistenceContext.getUsersManager();
    }

    @Override
    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mNetworkContext.getDeleterFactory().newDeleter(id);
    }

    @Override
    public ReviewsListView newReviewsListView(ReviewNode reviewNode, boolean withMenu) {
        return mFactoryReviewView.newReviewsListScreen(reviewNode,
                mFactoryReviewViewAdapter, withMenu);
    }
}
