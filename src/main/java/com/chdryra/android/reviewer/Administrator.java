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
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ObjectHolder;

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
 * <li>The packing/unpacking of review data needed to pass between activities</li>
 * <li>Publishing of reviews</li>
 * <li>List of social platforms</li>
 * </ul>
 *
 * @see com.chdryra.android.reviewer.Author
 * @see com.chdryra.android.reviewer.RCollectionReview
 */
public class Administrator {
    private static final String CONTROLLER_ID = "com.chdryra.android.reviewer.review_id";
    private static final Author AUTHOR        = new Author("Rizwan Choudrey");

    private static Administrator sAdministrator;

    private final Context                         mContext;
    private final ReviewCollectionAdapter<Review> mPublishedReviews;
    private final ObjectHolder                    mAdapters;

    private ReviewBuilder mReviewBuilder;

    private Administrator(Context context) {
        mContext = context;
        mAdapters = new ObjectHolder();
        mPublishedReviews = new ReviewCollectionAdapter<>();
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
        if (mReviewBuilder == null) createNewReviewInProgress();
        return mReviewBuilder;
    }

    public ReviewBuilder createNewReviewInProgress() {
        mReviewBuilder = new ReviewBuilder(mContext);
        return mReviewBuilder;
    }

    public ReviewCollectionAdapter<Review> getPublishedReviews() {
        return mPublishedReviews;
    }

    public void publishReviewInProgress() {
        mPublishedReviews.add(mReviewBuilder.publish(new Date()));
    }

    public GvSocialPlatformList getSocialPlatformList() {
        return GvSocialPlatformList.getLatest(mContext);
    }

    public void pack(GvAdapter adapter, Intent i) {
        i.putExtra(CONTROLLER_ID, adapter.getId());
        register(adapter);
    }

    public Bundle pack(GvAdapter adapter) {
        Bundle args = new Bundle();
        args.putString(CONTROLLER_ID, adapter.getId());
        register(adapter);
        return args;
    }

    public GvAdapter unpack(Intent i) {
        return unpack(i.getExtras());
    }

    public GvAdapter unpack(Bundle args) {
        GvAdapter adapter = args != null ? getAdapter(args.getString(CONTROLLER_ID))
                : null;
        unregister(adapter);

        return adapter;
    }

    private void register(GvAdapter adapter) {
        mAdapters.addObject(adapter.getId(), adapter);
    }

    private void unregister(GvAdapter adapter) {
        if (adapter != null) mAdapters.removeObject(adapter.getId());
    }

    private GvAdapter getAdapter(String id) {
        return id != null ? (GvAdapter) mAdapters.getObject(id) : null;
    }
}
