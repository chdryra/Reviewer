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

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.DataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewing.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;
import com.chdryra.android.reviewer.Models.Social.SocialPlatformList;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProvider;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
import com.chdryra.android.reviewer.View.Screens.Builders.BuilderChildListScreen;
import com.chdryra.android.reviewer.View.Utils.RequestCodeGenerator;

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
    private final ReviewerDb mDatabase;
    private ReviewBuilderAdapter mReviewBuilderAdapter;

    private ApplicationInstance(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call createWithApplicationContext first!");
    }

    private ApplicationInstance(Context context, ApplicationContext applicationContext) {
        super(context, NAME);
        mApplicationContext = applicationContext;
        mDatabase = applicationContext.getReviewerDb();
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

    public ReviewBuilderAdapter getReviewBuilderAdapter() {
        return mReviewBuilderAdapter;
    }

    public ReviewsProvider getReviewsProvider() {
        return mApplicationContext.getReviewsProvider();
    }

    public FactoryReviews getReviewsFactory() {
        return mApplicationContext.getReviewsFactory();
    }

    public DataConverters getDataConverters() {
        return mApplicationContext.getDataConverters();
    }

    public BuilderChildListScreen getBuilderChildListScreen() {
        return mApplicationContext.getBuilderChildListScreen();
    }

    public FactoryReviewViewAdapter getReviewViewAdapterFactory() {
        return mApplicationContext.getReviewViewAdapterFactory();
    }

    public SocialPlatformList getSocialPlatformList() {
        return mApplicationContext.getSocialPlatformList();
    }

    public ReviewBuilderAdapter newReviewBuilderAdapter() {
        FactoryReviewBuilderAdapter adapterfactory =
                mApplicationContext.getReviewBuilderAdapterFactory();
        mReviewBuilderAdapter = adapterfactory.newAdapter();
        return mReviewBuilderAdapter;
    }

    public void publishReviewBuilder() {
        Review published = mReviewBuilderAdapter.publishReview();
        mDatabase.addReviewToDb(published);
        mReviewBuilderAdapter = null;
    }

    public void deleteFromAuthorsFeed(String reviewId) {
        mDatabase.deleteReviewFromDb(reviewId);
    }

    public void launchReview(Activity activity, String reviewId) {
        ReviewsProvider repo = mApplicationContext.getReviewsProvider();
        Review review = repo.getReview(reviewId);
        Review reviewNode = mApplicationContext.getReviewsFactory().createMetaReview(review);
        FactoryReviewViewAdapter adapterFactory = mApplicationContext.getReviewViewAdapterFactory();
        LaunchableUi ui = adapterFactory.newReviewsListAdapter(reviewNode.getTreeRepresentation()).getReviewView();
        String tag = review.getSubject().getSubject();
        int requestCode = RequestCodeGenerator.getCode(tag);
        LauncherUi.launch(ui, activity, requestCode, tag, new Bundle());
    }
}
