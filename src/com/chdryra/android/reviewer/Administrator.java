/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Singleton that controls app-wide duties. Holds 4 main objects:
 * <ul>
 *     <li>Author: author credentials</li>
 *     <li>Published reviews: collection of published reviews to display on feed activity</li>
 *     <li>Review controller: controls editing of new reviews</li>
 *     <li>Context: application context for retrieving app data</li>
 * </ul>
 *
 * Also manages:
 * <ul>
 *     <li>The creation of new reviews</li>
 *     <li>The packing/unpacking of review data needed to pass between activities</li>
 *     <li>Publishing of reviews</li>
 *     <li>List of social platforms</li>
 * </ul>
 *
 * @see com.chdryra.android.reviewer.Author
 * @see com.chdryra.android.reviewer.RCollectionReview
 * @see com.chdryra.android.reviewer.Controller
 *
 */
class Administrator {
    private static Administrator sAdministrator;

    private final Author mCurrentAuthor = new Author("Rizwan Choudrey");
    private final RCollectionReview<ReviewNode> mPublishedReviews;
    private final Context    mContext;
    private       Controller mController;

    private Administrator(Context context) {
        mPublishedReviews = new RCollectionReview<ReviewNode>();
        mContext = context;
    }

    static Administrator get(Context c) {
        if (sAdministrator == null || c.getApplicationContext() != sAdministrator.mContext) {
            sAdministrator = new Administrator(c.getApplicationContext());
        }

        return sAdministrator;
    }

    GVReviewOverviewList getPublishedReviewsFeed() {
        return new ControllerReviewCollection<ReviewNode>(mPublishedReviews)
                .getGridViewablePublished();
    }

    ControllerReviewNodeExpandable createNewReviewInProgress() {
        mController = new Controller();
        return mController.getReviewInProgress();
    }

    ControllerReview unpack(Bundle args) {
        if (mController != null) {
            return mController.unpack(args);
        } else {
            return null;
        }
    }

    void pack(ControllerReviewNode controller, Intent intent) {
        if (mController != null) {
            mController.pack(controller, intent);
        }
    }

    Bundle pack(ControllerReviewNode controller) {
        if (mController != null) {
            return mController.pack(controller);
        } else {
            return null;
        }
    }

    void publishReviewInProgress() {
        ReviewTreePublisher publisher = new ReviewTreePublisher(mCurrentAuthor);
        ReviewNode published = mController.getReviewInProgress().publishAndTag(publisher);
        mPublishedReviews.add(published);
    }

    GVSocialPlatformList getSocialPlatformList() {
        return GVSocialPlatformList.getLatest(mContext);
    }
}
