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
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilder;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.View.GvDataModel.GvSocialPlatformList;
import com.chdryra.android.reviewer.View.Launcher.LaunchableUi;
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
 * @see ReviewIdableList
 */
public class Administrator extends ApplicationSingleton{
    private static final String NAME = "Administrator";
    private static final String  REVIEWVIEW_ID     = "com.chdryra.android.reviewer.review_id";
    private static final Author  AUTHOR            = new Author("Rizwan Choudrey", UserId
            .generateId());

    private static Administrator sAdministrator;

    private final ObjectHolder            mViews;
    private ReviewBuilder mReviewBuilder;

    private Administrator(Context context) {
        super(context, NAME);
        mViews = new ObjectHolder();
    }

    public static Administrator get(Context c) {
        if (sAdministrator == null) {
            sAdministrator = new Administrator(c);
        } else {
            sAdministrator.checkContextOrThrow(c);
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
        mReviewBuilder = new ReviewBuilder(getContext(), AUTHOR);
        return mReviewBuilder;
    }

    public void publishReviewBuilder() {
        ReviewNode published = mReviewBuilder.publish(PublishDate.now());
        ReviewFeed.addToFeed(getContext(), published);
        mReviewBuilder = null;
    }

    public LaunchableUi getReviewLaunchable(GvReviewId id) {
        return ReviewFeed.getReviewLaunchable(getContext(), id);
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
