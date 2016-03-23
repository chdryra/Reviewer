/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PersistenceContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ViewContext;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.BackendReviewUploader;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PresenterContextBasic implements PresenterContext{
    private ModelContext mModelContext;
    private ViewContext mViewContext;
    private SocialContext mSocialContext;
    private NetworkContext mNetworkContext;
    private PersistenceContext mPersistenceContext;

    private FactoryGvData mFactoryGvData;
    private FactoryReviewBuilderAdapter mFactoryBuilderAdapter;
    private FactoryReviewViewAdapter mFactoryReviewViewAdapter;
    private FactoryReviewViewLaunchable mFactoryReviewViewLaunchable;
    private ReviewBuilderAdapter<?> mReviewBuilderAdapter;

    protected PresenterContextBasic(ModelContext modelContext,
                                    ViewContext viewContext,
                                    SocialContext socialContext,
                                    NetworkContext networkContext,
                                    PersistenceContext persistenceContext) {
        mModelContext = modelContext;
        mViewContext = viewContext;
        mSocialContext = socialContext;
        mNetworkContext = networkContext;
        mPersistenceContext = persistenceContext;
    }

    public void setFactoryReviewViewLaunchable(FactoryReviewViewLaunchable
                                                       factoryReviewViewLaunchable) {
        mFactoryReviewViewLaunchable = factoryReviewViewLaunchable;
    }

    public void setFactoryGvData(FactoryGvData factoryGvData) {
        mFactoryGvData = factoryGvData;
    }


    public void setFactoryBuilderAdapter(FactoryReviewBuilderAdapter factoryBuilderAdapter) {
        mFactoryBuilderAdapter = factoryBuilderAdapter;
    }

    public void setFactoryReviewViewAdapter(FactoryReviewViewAdapter factoryReviewViewAdapter) {
        mFactoryReviewViewAdapter = factoryReviewViewAdapter;
    }

    @Override
    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mFactoryReviewViewAdapter;
    }

    @Override
    public FactoryGvData getGvDataFactory() {
        return mFactoryGvData;
    }

    @Override
    public FactoryReviewViewLaunchable getReviewViewLaunchableFactory() {
        return mFactoryReviewViewLaunchable;
    }

    @Override
    public ReviewsFeed getAuthorsFeed() {
        return mPersistenceContext.getAuthorsFeed();
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
    public ConfigUi getConfigDataUi() {
        return mViewContext.getUiConfig();
    }

    @Override
    public LaunchableUiLauncher getUiLauncher() {
        return mViewContext.getUiLauncher();
    }

    @Override
    public void addToUsersFeed(Review review) {
        mPersistenceContext.getAuthorsFeed().addReview(review);
    }

    @Override
    public void deleteFromUsersFeed(ReviewId id) {
        mPersistenceContext.getAuthorsFeed().removeReview(id);
    }

    @Override
    public void launchReview(Activity activity, ReviewId reviewId) {
        ReviewNode reviewNode = mPersistenceContext.getReviewsSource().asMetaReview(reviewId);
        if(reviewNode == null) return;
        LaunchableUi ui = mFactoryReviewViewLaunchable.newReviewsListScreen(reviewNode,
                mFactoryReviewViewAdapter);
        String tag = reviewNode.getSubject().getSubject();
        getUiLauncher().launch(ui, activity, RequestCodeGenerator.getCode(tag));
    }

    @Override
    public ReviewBuilderAdapter<?> newReviewBuilderAdapter() {
        mReviewBuilderAdapter = mFactoryBuilderAdapter.newAdapter();
        return mReviewBuilderAdapter;
    }

    @Override
    public ReviewBuilderAdapter<?> newReviewBuilderAdapter(Review template) {
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
    public <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mReviewBuilderAdapter.getDataBuilderAdapter(dataType);
    }

    @Override
    public Review getReview(ReviewId id) {
        return mPersistenceContext.getReviewsSource().getReview(id);
    }

    @Override
    public TagsManager getTagsManager() {
        return mModelContext.getTagsManager();
    }

    @Override
    public SocialPlatformsPublisher newSocialPublisher() {
        return mNetworkContext.getSocialUploaderFactory().getPublisher();
    }

    @Override
    public BackendReviewUploader newBackendUploader() {
        return mNetworkContext.getBackendUploaderFactory().getUploader();
    }

    @Override
    public ReviewsRepositoryMutable getBackendRepository() {
        return mPersistenceContext.getBackendRepository();
    }
}
