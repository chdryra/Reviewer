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
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryMessage;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryMutableCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.NetworkServices.Backend.BackendReviewUploader;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.View.Configs.ConfigUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

import java.util.Collection;

/**
 * Singleton that controls app-wide duties. Holds 3 main objects:
 * <ul>
 * <li>ApplicationContext: for app-wide dependency injection</li>
 * </ul>
 * <p/>
 */
public class ApplicationInstance extends ApplicationSingleton {
    public static final String APP_NAME = "Teeqr";
    private static final String NAME = "ApplicationInstance";
    private static ApplicationInstance sSingleton;

    private final PresenterContext mPresenterContext;
    private LocationServicesApi mLocationServices;

    public interface ReviewBuilderAdapterCallback {
        void onAdapterBuilt(ReviewBuilderAdapter<?> adapter, RepositoryMessage error);
    }

    private ApplicationInstance(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call create With ApplicationContext!");
    }

    private ApplicationInstance(Context context, ApplicationContext applicationContext) {
        super(context, NAME);
        mPresenterContext = applicationContext.getContext();
        mLocationServices = applicationContext.getLocationServices();
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
        return mPresenterContext.getAuthorsFeed();
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

    public void newReviewBuilderAdapter(final ReviewBuilderAdapterCallback callback,
                                        @Nullable String templateId) {
        if (templateId == null) {
            doReviewBuildAdapterCallback(null, RepositoryMessage.ok("Repo not needed"), callback);
            return;
        }

        getReview(templateId, new RepositoryCallback() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, RepositoryMessage result) {
                doReviewBuildAdapterCallback(review, result, callback);
            }

            @Override
            public void onCollectionFetchedFromRepo(Collection<Review> reviews, RepositoryMessage
                    result) {

            }
        });
    }

    public void discardReviewBuilderAdapter() {
        mPresenterContext.discardReviewBuilderAdapter();
    }

    public Review executeReviewBuilder() {
        return mPresenterContext.executeReviewBuilder();
    }

    public ReviewsRepositoryMutable getLocalRepository() {
        return mPresenterContext.getLocalRepository();
    }

    public ReviewsRepositoryMutable getBackendRepository() {
        return mPresenterContext.getBackendRepository();
    }

    public void deleteFromUsersFeed(ReviewId id, RepositoryMutableCallback callback) {
        mPresenterContext.deleteFromUsersFeed(id, callback);
    }

    public void launchReview(Activity activity, ReviewId reviewId) {
        mPresenterContext.launchReview(activity, reviewId);
    }

    public void getReview(ReviewId id, RepositoryCallback callback) {
        mPresenterContext.getReview(id, callback);
    }

    public void getReview(String reviewId, RepositoryCallback callback) {
        mPresenterContext.getReview(new DatumReviewId(reviewId), callback);
    }

    public SocialPlatformsPublisher newSocialPublisher() {
        return mPresenterContext.newSocialPublisher();
    }

    public BackendReviewUploader newBackendUploader() {
        return mPresenterContext.newBackendUploader();
    }

    private void doReviewBuildAdapterCallback(@Nullable Review review,
                                              RepositoryMessage error,
                                              ReviewBuilderAdapterCallback callback) {
        ReviewBuilderAdapter<?> adapter = review != null ?
                mPresenterContext.newReviewBuilderAdapter(review)
                : mPresenterContext.newReviewBuilderAdapter();
        callback.onAdapterBuilt(adapter, error);
    }
}
