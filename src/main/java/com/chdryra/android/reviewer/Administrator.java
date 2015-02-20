/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.app.Activity;
import android.content.Context;

import java.util.Date;

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
 * @see com.chdryra.android.reviewer.Author
 * @see com.chdryra.android.reviewer.RCollectionReview
 */
public class Administrator {
    private static final Author AUTHOR = new Author("Rizwan Choudrey");
    private static final String FEED   = "Feed";

    private static Administrator sAdministrator;

    private final Context                 mContext;
    private final ReviewCollectionAdapter mPublishedReviews;

    private ReviewBuilder mReviewBuilder;

    private Administrator(Context context) {
        mContext = context;
        mPublishedReviews = new ReviewCollectionAdapter(AUTHOR, new Date(), FEED);
    }

    public static Administrator get(Context c) {
        if (sAdministrator == null) {
            sAdministrator = new Administrator(c.getApplicationContext());
        } else if (c.getApplicationContext() != sAdministrator.mContext) {
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

    public ReviewBuilder newReviewBuilder(Activity activity) {
        mReviewBuilder = new ReviewBuilder(activity);
        return mReviewBuilder;
    }

    public ReviewCollectionAdapter getPublishedReviews() {
        return mPublishedReviews;
    }

    public ViewReviewAdapter getShareScreenAdapter() {
        return new ShareScreenAdapter(mContext, mReviewBuilder);
    }

    public void publishReviewBuilder() {
        mPublishedReviews.add(mReviewBuilder.publish(new Date()));
        mReviewBuilder = null;
    }

    public GvSocialPlatformList getSocialPlatformList() {
        return GvSocialPlatformList.getLatest(mContext);
    }
}
