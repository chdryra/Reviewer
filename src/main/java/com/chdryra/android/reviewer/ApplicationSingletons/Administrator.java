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
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.ObjectHolder;
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
import com.chdryra.android.reviewer.View.Screens.ReviewView;
import com.chdryra.android.reviewer.View.Utils.ImageChooser;

import java.util.UUID;

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
public class Administrator extends ApplicationSingleton{
    private static final String NAME = "Administrator";
    private static final String  REVIEWVIEW_ID     = "com.chdryra.android.reviewer.review_id";
    private static final Author  AUTHOR            = new Author("Rizwan Choudrey", UserId
            .generateId());

    private static Administrator sSingleton;

    private final ObjectHolder            mViews;
    private final ReviewerDbProvider mDatabase;
    private final ReviewsRepository mReviewsRepository;
    private final TagsManager mTagsManager;
    private ReviewBuilderAdapter mReviewBuilderAdapter;

    private Administrator(Context context) {
        super(context, NAME);
        mViews = new ObjectHolder();
        mTagsManager = new TagsManager();
        mDatabase = new ReviewerDbProvider(ReviewerDb.getTestDatabase(context, mTagsManager));
        mReviewsRepository = new ReviewsRepository(mDatabase, AUTHOR);
    }

    public static Administrator get(Context c) {
        sSingleton = getSingleton(sSingleton, Administrator.class, c);
        return sSingleton;
    }

    public static ImageChooser getImageChooser(Activity activity) {
        Administrator admin = get(activity);
        ImageChooser chooser = null;
        if (admin.mReviewBuilderAdapter != null) {
            chooser = admin.mReviewBuilderAdapter.getImageChooser(activity);
        }

        return chooser;
    }

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

    public ReviewBuilderAdapter newReviewBuilder() {
        mReviewBuilderAdapter = new ReviewBuilderAdapter(getContext(), AUTHOR, mTagsManager);
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

    public GvSocialPlatformList getSocialPlatformList() {
        return GvSocialPlatformList.getLatest(getContext());
    }

    public void packView(ReviewView view, Intent i) {
        String id = UUID.randomUUID().toString();
        mViews.addObject(id, view);
        i.putExtra(REVIEWVIEW_ID, id);
    }

    public ReviewView unpackView(Intent i) {
        String id = i.getStringExtra(REVIEWVIEW_ID);
        ReviewView view = (ReviewView) mViews.getObject(id);
        mViews.removeObject(id);

        return view;
    }
}
