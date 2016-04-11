/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api
        .LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BackendService.BackendRepoService;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumUserId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.AuthorsStamp;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

/**
 * Singleton that controls app-wide duties.
 */
public class ApplicationInstance extends ApplicationSingleton {
    public static final String APP_NAME = "Teeqr";

    private static final DatumAuthor AUTHOR = new DatumAuthor("Rizwan Choudrey", new DatumUserId("123"));
    private static final String NAME = "ApplicationInstance";

    private static ApplicationInstance sSingleton;

    private final ReviewPacker mReviewPacker;
    private final PresenterContext mPresenterContext;
    private final LocationServicesApi mLocationServices;

    private ReviewsFeed mFeed;

    private ApplicationInstance(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call newInstance(.)!");
    }

    private ApplicationInstance(Context context, ApplicationContext applicationContext) {
        super(context, NAME);
        mPresenterContext = applicationContext.getContext();
        mLocationServices = applicationContext.getLocationServices();
        mReviewPacker = new ReviewPacker();
        setUser(AUTHOR);
    }

    //Static methods
    public static void newInstance(Context context,
                                   ApplicationContext applicationContext) {
        sSingleton = new ApplicationInstance(context, applicationContext);
    }

    public static ApplicationInstance getInstance(Context context) {
        sSingleton = getSingleton(sSingleton, ApplicationInstance.class, context);
        return sSingleton;
    }

    public ReviewBuilderAdapter<? extends GvDataList<?>> getReviewBuilderAdapter() {
        return mPresenterContext.getReviewBuilderAdapter();
    }

    public ReviewsFeed getUsersFeed() {
        return mFeed;
    }

    public FactoryReviews getReviewsFactory() {
        return mPresenterContext.getReviewsFactory();
    }

    public SocialPlatformList getSocialPlatformList() {
        return mPresenterContext.getSocialPlatformList();
    }

    public ConfigUi getConfigDataUi() {
        return mPresenterContext.getConfigDataUi();
    }

    public LaunchableUiLauncher getUiLauncher() {
        return mPresenterContext.getUiLauncher();
    }

    public FactoryReviewViewLaunchable getLaunchableFactory() {
        return mPresenterContext.getReviewViewLaunchableFactory();
    }

    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mPresenterContext.getReviewViewAdapterFactory();
    }

    public FactoryReviewViewParams getParamsFactory() {
        return getLaunchableFactory().getParamsFactory();
    }

    public FactoryGvData getGvDataFactory() {
        return mPresenterContext.getGvDataFactory();
    }

    public LocationServicesApi getLocationServices() {
        return mLocationServices;
    }

    public TagsManager getTagsManager() {
        return mPresenterContext.getTagsManager();
    }

    public <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mPresenterContext.getDataBuilderAdapter(dataType);
    }

    public ReviewBuilderAdapter<?> newReviewBuilderAdapter(@Nullable Review template) {
        return mPresenterContext.newReviewBuilderAdapter(template);
    }

    public void discardReviewBuilderAdapter() {
        mPresenterContext.discardReviewBuilderAdapter();
    }

    public Review executeReviewBuilder() {
        return mPresenterContext.executeReviewBuilder();
    }

    public void packReview(Review review, Bundle args) {
        mReviewPacker.packReview(review, args);
    }

    public @Nullable Review unpackReview(Bundle args) {
        return mReviewPacker.unpackReview(args);
    }

    public ReviewPublisher getPublisher() {
        return mPresenterContext.getReviewPublisher();
    }

    public void launchReview(Activity activity, ReviewId reviewId) {
        mPresenterContext.launchReview(activity, reviewId);
    }

    public void getReview(ReviewId id, CallbackRepository callback) {
        mPresenterContext.getReview(id, callback);
    }

    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mPresenterContext.newReviewDeleter(id);
    }

    public ReviewsRepositoryMutable getBackendRepository(BackendRepoService service) {
        // to ensure only used by BackendRepoService
        return mPresenterContext.getBackendRepository();
    }

    private void setUser(DatumAuthor user) {
        FactoryReviews reviewsFactory = getReviewsFactory();
        reviewsFactory.setAuthorsStamp(new AuthorsStamp(user));
        mFeed = mPresenterContext.getFeedFactory().newMutableFeed(reviewsFactory);
    }
}
