/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.ApplicationContexts.ApplicationContext;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.Utils.ImageChooser;

/**
 * Singleton that controls app-wide duties. Holds 4 main objects:
 * <ul>
 * <li>Author: author credentials</li>
 * <li>Published reviews: collection of published reviews to display on feed activity</li>
 * <li>Review adapter: controls editing of new reviews</li>
 * <li>Context: application context for retrieving app data</li>
 * </ul>
 * <p/>
 * Also manages:
 * <ul>
 * <li>The creation of new reviews</li>
 * <li>Publishing of reviews</li>
 * <li>List of social platforms</li>
 * </ul>
 *
 * @see Author
 * @see IdableList
 */
public class Administrator extends ApplicationSingleton {
    private static final String NAME = "Administrator";

    private static Administrator sSingleton;

    private final Author mAuthor;
    private final ReviewerDb mDatabase;
    private final ReviewsRepository mReviewsRepository;
    private final TagsManager mTagsManager;
    private final GvSocialPlatformList mSocialPlatformList;
    private ReviewBuilderAdapter mReviewBuilderAdapter;

    private Administrator(Context context) {
        super(context, NAME);
        throw new IllegalStateException("Need to call createWithApplicationContext first!");
    }

    private Administrator(Context context, ApplicationContext applicationContext) {
        super(context, NAME);
        mAuthor = applicationContext.getAuthor();
        mTagsManager = applicationContext.getTagsManager();
        mDatabase = applicationContext.getDataBase();
        mReviewsRepository = applicationContext.getReviewsRepository();
        mSocialPlatformList = new GvSocialPlatformList(applicationContext.getSocialPlatformList());
    }

    //Static methods
    public static Administrator createWithApplicationContext(Context context,
                                                    ApplicationContext applicationContext) {
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
    public ImageChooser getImageChooser(Context context) {
        ImageChooser chooser = null;
        if (mReviewBuilderAdapter != null) {
            chooser = mReviewBuilderAdapter.getImageChooser(context);
        }

        return chooser;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public ReviewsRepository getReviewsRepository() {
        return mReviewsRepository;
    }

    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    public ReviewBuilderAdapter getReviewBuilder() {
        return mReviewBuilderAdapter;
    }

    public GvSocialPlatformList getSocialPlatformList() {
        return mSocialPlatformList;
    }

    public ReviewBuilderAdapter newReviewBuilder() {
        ReviewBuilder builder = new ReviewBuilder(getAuthor(), getTagsManager());
        mReviewBuilderAdapter = new ReviewBuilderAdapter(getContext(), builder);
        return mReviewBuilderAdapter;
    }

    public void publishReviewBuilder() {
        Review published = mReviewBuilderAdapter.publish();
        mDatabase.addReviewToDb(published);
        mReviewBuilderAdapter = null;
    }

    public void deleteFromAuthorsFeed(String reviewId) {
        mDatabase.deleteReviewFromDb(reviewId);
    }
}
