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
import android.os.Bundle;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .DataBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories
        .FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Models.Social.Interfaces.SocialPlatformList;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.Configs.Interfaces.ConfigDataUi;
import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvData;
import com.chdryra.android.reviewer.View.Factories.FactoryLaunchableUi;
import com.chdryra.android.reviewer.View.Factories.FactoryLauncherUi;
import com.chdryra.android.reviewer.View.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.Interfaces.LauncherUi;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Builders.BuilderChildListView;
import com.chdryra.android.reviewer.View.Implementation.ReviewViewModel.Factories.FactoryReviewViewParams;

/**
 * Singleton that controls app-wide duties. Holds 3 main objects:
 * <ul>
 * <li>ApplicationContext: for app-wide dependency injection</li>
 * <li>ReviewerDb: on-phone cache</li>
 * <li>ReviewBuilderAdapter: controls editing of new reviews</li>
 * </ul>
 * <p/>
 * Also manages:
 * <ul>
 * <li>The creation of new reviews</li>
 * <li>Publishing of reviews</li>
 * <li>List of social platforms</li>
 * <li>Launching of reviews form repository</li>
 * </ul>
 *
 */
public class ApplicationInstance extends ApplicationSingleton {
    private static final String NAME = "ApplicationInstance";

    private static ApplicationInstance sSingleton;

    private final ApplicationContext mApplicationContext;
    private ReviewBuilderAdapter<?> mReviewBuilderAdapter;

    private ApplicationInstance(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call createWithApplicationContext first!");
    }

    private ApplicationInstance(Context context, ApplicationContext applicationContext) {
        super(context, NAME);
        mApplicationContext = applicationContext;
    }

    //Static methods
    public static void createWithApplicationContext(Context context, ApplicationContext
                                                                     applicationContext) {
        sSingleton = new ApplicationInstance(context, applicationContext);
    }

    public static ApplicationInstance getInstance(Context c) {
        sSingleton = getSingleton(sSingleton, ApplicationInstance.class, c);
        return sSingleton;
    }

    //public methods
    public DataValidator getDataValidator() {
        return mApplicationContext.getDataValidator();
    }

    public ReviewBuilderAdapter<?> getReviewBuilderAdapter() {
        return mReviewBuilderAdapter;
    }

    public <T extends GvData> DataBuilderAdapter<T> getDataBuilderAdapter(GvDataType<T> dataType) {
        return mReviewBuilderAdapter.getDataBuilderAdapter(dataType);
    }

    public ReviewsFeedMutable getAuthorsFeed() {
        return mApplicationContext.getAuthorsFeed();
    }

    public FactoryReviews getReviewsFactory() {
        return mApplicationContext.getReviewsFactory();
    }

    public BuilderChildListView getBuilderChildListScreen() {
        return mApplicationContext.getBuilderChildListView();
    }

    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mApplicationContext.getReviewViewAdapterFactory();
    }

    public SocialPlatformList getSocialPlatformList() {
        return mApplicationContext.getSocialPlatformList();
    }

    public ReviewBuilderAdapter<?> newReviewBuilderAdapter() {
        FactoryReviewBuilderAdapter adapterfactory =
                mApplicationContext.getReviewBuilderAdapterFactory();
        mReviewBuilderAdapter = adapterfactory.newAdapter();
        return mReviewBuilderAdapter;
    }

    public void publishReviewBuilder() {
        Review published = mReviewBuilderAdapter.publishReview();
        getAuthorsFeed().addReview(published);
        mReviewBuilderAdapter = null;
    }

    public void launchReview(Activity activity, String reviewId) {
        Review review = getAuthorsFeed().getReview(reviewId);
        ReviewNode reviewNode = getReviewsFactory().createMetaReview(review).getTreeRepresentation();
        FactoryReviewViewAdapter adapterFactory = mApplicationContext.getReviewViewAdapterFactory();
        LaunchableUi ui = getLaunchableFactory().newReviewsListScreen(reviewNode, adapterFactory);
        String tag = review.getSubject().getSubject();
        int requestCode = RequestCodeGenerator.getCode(tag);
        ui.launch(newLauncher(activity, requestCode, tag));
    }

    public ConfigDataUi getConfigDataUi() {
        return mApplicationContext.getUiConfig();
    }

    public FactoryLaunchableUi getLaunchableFactory() {
        return mApplicationContext.getLaunchableFactory();
    }

    public FactoryLauncherUi getLauncherFactory() {
        return mApplicationContext.getLauncherFactory();
    }

    public FactoryReviewViewParams getParamsFactory() {
        return mApplicationContext.getParamsFactory();
    }

    public FactoryGvData getGvDataFactory() {
        return mApplicationContext.getGvDataFactory();
    }

    public LauncherUi newLauncher(Activity activity, int requestCode, String tag, Bundle args) {
        return getLauncherFactory().newLauncher(activity, requestCode, tag, args);
    }

    private LauncherUi newLauncher(Activity activity, int requestCode, String tag) {
        return getLauncherFactory().newLauncher(activity, requestCode, tag);
    }
}
