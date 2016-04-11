/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
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
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackReviewsSource;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

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
        mPersistenceContext = persistenceContext;
        mNetworkContext = networkContext;
        mPublisher = mNetworkContext.getPublisherFactory().newPublisher(mPersistenceContext
                .getLocalRepository());
    }

    public void setFactoryReviewViewLaunchable(FactoryReviewViewLaunchable
                                                       factoryReviewViewLaunchable) {
        mFactoryReviewViewLaunchable = factoryReviewViewLaunchable;
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
    public FactoryReviewsFeed getFeedFactory() {
        return mPersistenceContext.getFeedFactory();
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
    public void launchReview(final Activity activity, ReviewId reviewId) {
        mPersistenceContext.getReviewsSource().asMetaReview(reviewId, new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                if (review != null) launchReview(activity, review);
            }
        });

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
    public <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mReviewBuilderAdapter.getDataBuilderAdapter(dataType);
    }

    @Override
    public void getReview(ReviewId id, CallbackRepository callback) {
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
    public ReviewsRepositoryMutable getBackendRepository() {
        return mPersistenceContext.getBackendRepository();
    }

    @Override
    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mNetworkContext.getDeleterFactory().newDeleter(id);
    }

    private void launchReview(Activity activity, ReviewNode reviewNode) {
        LaunchableUi ui = mFactoryReviewViewLaunchable.newReviewsListScreen(reviewNode,
                mFactoryReviewViewAdapter);
        String tag = reviewNode.getSubject().getSubject();
        getUiLauncher().launch(ui, activity, RequestCodeGenerator.getCode(tag));
    }
}
