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

public class Administrator {
    private static Administrator sAdministrator;

    private final Author mCurrentAuthor = new Author("Rizwan Choudrey");
    private final RCollectionReviewNode mPublishedReviews;
    private final Context    mContext;
    private       Controller mController;

    private Administrator(Context context) {
        mPublishedReviews = new RCollectionReviewNode();
        mContext = context;
    }

    public static Administrator get(Context c) {
        if (sAdministrator == null || c.getApplicationContext() != sAdministrator.mContext) {
            sAdministrator = new Administrator(c.getApplicationContext());
        }

        return sAdministrator;
    }

    public GVReviewOverviewList getFeed() {
        return new ControllerReviewNodeCollection(mPublishedReviews).getGridViewablePublished();
    }

    public ControllerReviewNode createNewReviewInProgress() {
        mController = new Controller();
        return mController.getReviewInProgress();
    }

    public ControllerReviewNode unpack(Bundle args) {
        if (mController != null) {
            return mController.unpack(args);
        } else {
            return null;
        }
    }

    public void pack(ControllerReviewNode controller, Intent intent) {
        if (mController != null) {
            mController.pack(controller, intent);
        }
    }

    public Bundle pack(ControllerReviewNode controller) {
        if (mController != null) {
            return mController.pack(controller);
        } else {
            return null;
        }
    }

    public void publishReviewInProgress() {
        ReviewTreePublisher publisher = new ReviewTreePublisher(mCurrentAuthor);
        ReviewNode published = mController.getReviewInProgress().publishAndTag(publisher);
        mPublishedReviews.add(published);
    }

    public GVSocialPlatformList getSocialPlatformList() {
        return GVSocialPlatformList.getLatest(mContext);
    }
}
