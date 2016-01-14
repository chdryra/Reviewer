/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.app.Activity;
import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.LocationServices.Interfaces.ReviewerLocationServices;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.Social.SocialPlatformList;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.DataBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewLaunchable;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewParams;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;
import com.chdryra.android.reviewer.View.Configs.ConfigDataUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

/**
 * Singleton that controls app-wide duties. Holds 3 main objects:
 * <ul>
 * <li>ApplicationContext: for app-wide dependency injection</li>
 * </ul>
 * <p/>
 */
public class ApplicationInstance extends ApplicationSingleton {
    private static final String NAME = "ApplicationInstance";

    private static ApplicationInstance sSingleton;

    private final PresenterContext mPresenterContext;
    private ReviewerLocationServices mLocationServices;

    private ApplicationInstance(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call createWithApplicationContext!");
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

    public ReviewBuilderAdapter<?> getReviewBuilderAdapter() {
        return mPresenterContext.getReviewBuilderAdapter();
    }

    public ReviewsFeed getAuthorsFeed() {
        return mPresenterContext.getAuthorsFeed();
    }

    public FactoryReviews getReviewsFactory() {
        return mPresenterContext.getReviewsFactory();
    }

    public SocialPlatformList getSocialPlatformList() {
        return mPresenterContext.getSocialPlatformList();
    }

    public ConfigDataUi getConfigDataUi() {
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

    public <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mPresenterContext.getDataBuilderAdapter(dataType);
    }

    public ReviewBuilderAdapter<?> newReviewBuilderAdapter() {
        return mPresenterContext.newReviewBuilderAdapter();
    }

    public void publishReviewBuilder() {
        mPresenterContext.publishReviewBuilder();
    }

    public void deleteFromAuthorsFeed(ReviewId id) {
        mPresenterContext.deleteFromAuthorsFeed(id);
    }

    public void launchReview(Activity activity, ReviewId reviewId) {
        mPresenterContext.launchReview(activity, reviewId);
    }

    public ReviewerLocationServices getLocationServices() {
        return mLocationServices;
    }
}
