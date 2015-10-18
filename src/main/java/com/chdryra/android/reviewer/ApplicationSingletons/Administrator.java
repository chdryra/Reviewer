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
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewerDbProvider;
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
    private static final Author AUTHOR = new Author("Rizwan Choudrey", UserId
            .generateId());

    private static Administrator sSingleton;

    private final ReviewerDb mDatabase;
    private final ReviewsRepository mReviewsRepository;
    private final TagsManager mTagsManager;
    private ReviewBuilderAdapter mReviewBuilderAdapter;

    private Administrator(Context context) {
        super(context, NAME);
        mTagsManager = new TagsManager();
        mDatabase = ReviewerDb.getTestDatabase(context, mTagsManager);
        ReviewerDbProvider provider = new ReviewerDbProvider(mDatabase);
        mDatabase.registerObserver(provider);
        mReviewsRepository = new ReviewsRepository(provider, AUTHOR);
    }

    //Static methods
    public static Administrator get(Context c) {
        sSingleton = getSingleton(sSingleton, Administrator.class, c);
        return sSingleton;
    }

    public static ImageChooser getImageChooser(Context context) {
        Administrator admin = get(context);
        ImageChooser chooser = null;
        if (admin.mReviewBuilderAdapter != null) {
            chooser = admin.mReviewBuilderAdapter.getImageChooser(context);
        }

        return chooser;
    }

    //public methods
    public Author getAuthor() {
        return AUTHOR;
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
        return GvSocialPlatformList.getLatest(getContext());
    }

    public ReviewBuilderAdapter newReviewBuilder() {
        ReviewBuilder builder = new ReviewBuilder(getContext(), getAuthor(), getTagsManager());
        mReviewBuilderAdapter = new ReviewBuilderAdapter(builder);
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
