/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendService.BackendRepoService;
import com.chdryra.android.reviewer.Authentication.Implementation.UsersManager;
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
    private static final String NAME = "ApplicationInstance";

    private static ApplicationInstance sSingleton;

    private final ReviewPacker mReviewPacker;
    private final PresenterContext mAppContext;
    private final LocationServicesApi mLocationServices;
    private final UserContext mUser;

    private ApplicationInstance(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call newInstance(.)!");
    }

    private ApplicationInstance(Context androidContext,
                                ApplicationContext applicationContext,
                                UserContext userContext) {
        super(androidContext, NAME);
        mAppContext = applicationContext.getContext();
        mLocationServices = applicationContext.getLocationServices();
        mUser = userContext;
        mReviewPacker = new ReviewPacker();
    }

    //Static methods
    public static void newInstance(Context androidContext,
                                   ApplicationContext applicationContext,
                                   UserContext userContext) {
        sSingleton = new ApplicationInstance(androidContext, applicationContext, userContext);
    }

    public static ApplicationInstance getInstance(Context context) {
        sSingleton = getSingleton(sSingleton, ApplicationInstance.class, context);
        return sSingleton;
    }

    public ReviewBuilderAdapter<? extends GvDataList<?>> getReviewBuilderAdapter() {
        return mAppContext.getReviewBuilderAdapter();
    }

    public ReviewsFeed getUsersFeed() {
        return mAppContext.getFeedFactory().newFeed(getReviewsFactory());
    }

    public FactoryReviews getReviewsFactory() {
        return mAppContext.getReviewsFactory();
    }

    public SocialPlatformList getSocialPlatformList() {
        return mAppContext.getSocialPlatformList();
    }

    public ConfigUi getConfigUi() {
        return mAppContext.getConfigUi();
    }

    public LaunchableUiLauncher getUiLauncher() {
        return mAppContext.getUiLauncher();
    }

    public FactoryReviewViewLaunchable getLaunchableFactory() {
        return mAppContext.getReviewViewLaunchableFactory();
    }

    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mAppContext.getReviewViewAdapterFactory();
    }

    public FactoryReviewViewParams getParamsFactory() {
        return getLaunchableFactory().getParamsFactory();
    }

    public FactoryGvData getGvDataFactory() {
        return mAppContext.getGvDataFactory();
    }

    public LocationServicesApi getLocationServices() {
        return mLocationServices;
    }

    public TagsManager getTagsManager() {
        return mAppContext.getTagsManager();
    }

    public ReviewPublisher getPublisher() {
        return mAppContext.getReviewPublisher();
    }

    public UsersManager getUsersManager() {
        return mAppContext.getUsersManager();
    }

    public <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mAppContext.getDataBuilderAdapter(dataType);
    }

    public ReviewBuilderAdapter<?> newReviewBuilderAdapter(@Nullable Review template) {
        return mAppContext.newReviewBuilderAdapter(template);
    }

    public void discardReviewBuilderAdapter() {
        mAppContext.discardReviewBuilderAdapter();
    }

    public Review executeReviewBuilder() {
        return mAppContext.executeReviewBuilder();
    }

    public void packReview(Review review, Bundle args) {
        mReviewPacker.packReview(review, args);
    }

    @Nullable
    public Review unpackReview(Bundle args) {
        return mReviewPacker.unpackReview(args);
    }

    public void launchReview(Activity activity, ReviewId reviewId) {
        mAppContext.launchReview(activity, reviewId);
    }

    public void getReview(ReviewId id, CallbackRepository callback) {
        mAppContext.getReview(id, callback);
    }

    public ReviewDeleter newReviewDeleter(ReviewId id) {
        return mAppContext.newReviewDeleter(id);
    }

    public ReviewsRepositoryMutable getBackendRepository(BackendRepoService service) {
        // to ensure only used by BackendRepoService
        return mAppContext.getBackendRepository();
    }

    public UserContext getUserContext() {
        return mUser;
    }

    public void logout(Activity activity) {
        mUser.logout(activity);
    }
}
