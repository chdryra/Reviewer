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
 * <li>Review controller: controls editing of new reviews</li>
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

    private final Context             mContext;
    private final PublishedReviews    mPublishedReviews;
    private final ObjectHolder        mControllers;

    private ControllerReviewBuilder mReviewBuilder;

    private Administrator(Context context) {
        mContext = context;
        mControllers = new ObjectHolder();
        mPublishedReviews = new PublishedReviews();
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

    public ControllerReviewBuilder getReviewBuilder() {
        return mReviewBuilder;
    }

    public ControllerReviewBuilder createNewReviewInProgress() {
        mReviewBuilder = new ControllerReviewBuilder(mContext);
        return mReviewBuilder;
    }

    public PublishedReviews getPublishedReviews() {
        return mPublishedReviews;
    }

    public void publishReviewInProgress() {
        Review p = mReviewBuilder.publish(AUTHOR, new Date());
        mPublishedReviews.add(p);
    }

    public GvSocialPlatformList getSocialPlatformList() {
        return GvSocialPlatformList.getLatest(mContext);
    }

    public void pack(ControllerReview controller, Intent i) {
        i.putExtra(CONTROLLER_ID, controller.getId());
        register(controller);
    }

    public Bundle pack(ControllerReview controller) {
        Bundle args = new Bundle();
        args.putString(CONTROLLER_ID, controller.getId());
        register(controller);
        return args;
    }

    public ControllerReview unpack(Intent i) {
        return unpack(i.getExtras());
    }

    public ControllerReview unpack(Bundle args) {
        ControllerReview controller = args != null ? getController(args.getString(CONTROLLER_ID))
                : null;
        unregister(controller);

        return controller;
    }

    private void register(ControllerReview controller) {
        mControllers.addObject(controller.getId(), controller);
    }

    private void unregister(ControllerReview controller) {
        if (controller != null) mControllers.removeObject(controller.getId());
    }

    private ControllerReview getController(String id) {
        return id != null ? (ControllerReview) mControllers.getObject(id) : null;
    }
}
