/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.ObjectHolder;
import com.chdryra.android.reviewer.Database.ReviewerDb;
import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.UserId;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.ImageChooser;

import java.util.Date;
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
 * @see com.chdryra.android.reviewer.Model.Author
 * @see ReviewIdableList
 */
public class Administrator {
    private static final String REVIEWVIEW_ID = "com.chdryra.android.reviewer.review_id";
    private static final Author AUTHOR        = new Author("Rizwan Choudrey", UserId.generateId());
    private static final boolean USE_TEST_DATABASE = true;

    private static Administrator sAdministrator;

    private final Context                 mContext;
    private final ReviewFeedAdapter mPublishedReviews;
    private final ObjectHolder      mViews;
    private       ReviewBuilder     mReviewBuilder;
    private       ReviewerDb        mDatabase;

    private Administrator(Context context) {
        mContext = context;
        mViews = new ObjectHolder();
        mDatabase = getDatabase();
        ReviewIdableList<ReviewNode> published = mDatabase.getReviewTreesFromDb();
        mPublishedReviews = new ReviewFeedAdapter(mContext, AUTHOR.getName());
        for (ReviewNode node : published) {
            mPublishedReviews.add(node);
        }
        mDatabase.loadTags();
    }

    public static Administrator get(Context c) {
        if (sAdministrator == null) {
            sAdministrator = new Administrator(c);
        } else if (c.getApplicationContext() != sAdministrator.mContext.getApplicationContext()) {
            throw new RuntimeException("Can only have 1 Administrator per application!");
        }

        return sAdministrator;
    }

    public static ImageChooser getImageChooser(Activity activity) {
        Administrator admin = get(activity);
        ImageChooser chooser = null;
        if (admin.mReviewBuilder != null) {
            chooser = admin.mReviewBuilder.getImageChooser(activity);
        }

        return chooser;
    }

    public Author getAuthor() {
        return AUTHOR;
    }

    public ReviewBuilder getReviewBuilder() {
        return mReviewBuilder;
    }

    public ReviewBuilder newReviewBuilder() {
        mReviewBuilder = new ReviewBuilder(mContext, AUTHOR);
        return mReviewBuilder;
    }

    public ReviewViewAdapter getPublishedReviews() {
        return mPublishedReviews;
    }

    public ReviewViewAdapter getPublishedReview(String reviewId) {
        return mPublishedReviews.expandReview(ReviewId.fromString(reviewId));
    }

    public void publishReviewBuilder() {
        ReviewNode published = mReviewBuilder.publish(new Date());
        mDatabase.addReviewTreeToDb(published);
        mPublishedReviews.add(published);
        mReviewBuilder = null;
    }

    public GvImageList getCoversForReview(String reviewId) {
        return MdGvConverter.convert(getReview(reviewId).getImages().getCovers());
    }

    public void deleteReview(String reviewId) {
        mPublishedReviews.delete(ReviewId.fromString(reviewId));
        mDatabase.deleteReviewTreeFromDb(reviewId);
    }

    public GvSocialPlatformList getSocialPlatformList() {
        return GvSocialPlatformList.getLatest(mContext);
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

    private Review getReview(String reviewId) {
        return mPublishedReviews.get(ReviewId.fromString(reviewId));
    }

    private ReviewerDb getDatabase() {
        if (USE_TEST_DATABASE) {
            return ReviewerDb.getTestDatabase(mContext);
        } else {
            return ReviewerDb.getDatabase(mContext);
        }
    }
}
