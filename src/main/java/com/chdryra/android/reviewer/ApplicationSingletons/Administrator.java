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

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.ApplicationContext;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.Social.SocialPlatformList;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
import com.chdryra.android.reviewer.View.Launcher.LauncherUi;
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
public class Administrator extends ApplicationSingleton {
    private static final String NAME = "Administrator";

    private static Administrator sSingleton;

    private final ApplicationContext mApplicationContext;
    private final ReviewerDb mDatabase;
    private ReviewBuilderAdapter mReviewBuilderAdapter;

    private Administrator(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call createWithApplicationContext first!");
    }

    private Administrator(Context context, ApplicationContext applicationContext) {
        super(context, NAME);
        mApplicationContext = applicationContext;
        mDatabase = applicationContext.getReviewerDb();
    }

    //Static methods
    public static Administrator createWithApplicationContext(Context context,
                                                             ApplicationContext
                                                                     applicationContext) {
        return new Administrator(context, applicationContext);
    }

    public static void setAsAdministrator(Administrator administrator) {
        sSingleton = administrator;
    }

    public static Administrator getInstance(Context c) {
        sSingleton = getSingleton(sSingleton, Administrator.class, c);
        return sSingleton;
    }

    //public methods
    public ApplicationContext getApplicationContext() {
        return mApplicationContext;
    }

    public ReviewsRepository getReviewsRepository() {
        return mApplicationContext.getReviewsRepository();
    }

    public ReviewBuilderAdapter getReviewBuilderAdapter() {
        return mReviewBuilderAdapter;
    }

    public SocialPlatformList getSocialPlatformList() {
        return mApplicationContext.getSocialPlatformList();
    }

    public void setReviewBuilderAdapter(ReviewBuilderAdapter reviewBuilderAdapter) {
        mReviewBuilderAdapter = reviewBuilderAdapter;
    }

    public void publishReviewBuilder() {
        Review published = mReviewBuilderAdapter.publish();
        mDatabase.addReviewToDb(published);
        mReviewBuilderAdapter = null;
    }

    public void deleteFromAuthorsFeed(String reviewId) {
        mDatabase.deleteReviewFromDb(reviewId);
    }

    public void launchReview(Activity activity, GvReviewId id) {
        ReviewsRepository repo = getReviewsRepository();
        Review review = repo.getReview(id);
        ReviewNode reviewNode = mApplicationContext.getReviewFactory().createMetaReview(review);
        FactoryReviewViewAdapter adapterFactory = mApplicationContext.getReviewViewAdapterFactory();
        LaunchableUi ui = adapterFactory.newReviewsListAdapter(reviewNode).getReviewView();
        String tag = review.getSubject().getSubject();
        int requestCode = RequestCodeGenerator.getCode(tag);
        LauncherUi.launch(ui, activity, requestCode, tag, new Bundle());
    }
}
