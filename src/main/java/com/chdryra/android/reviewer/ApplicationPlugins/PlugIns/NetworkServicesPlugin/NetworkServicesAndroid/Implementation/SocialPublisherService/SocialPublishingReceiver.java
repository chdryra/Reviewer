/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid
        .Implementation.SocialPublisherService;


import android.content.Context;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BackendService.BackendRepoService;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BroadcastingServiceReceiver;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.SocialUploader;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPublishingReceiver extends BroadcastingServiceReceiver<SocialUploader.Listener> {
    private final ReviewId mReviewId;

    public SocialPublishingReceiver(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String id = intent.getStringExtra(BackendRepoService.REVIEW_ID);
        DatumReviewId reviewId = new DatumReviewId(id);

        if (!mReviewId.equals(reviewId)) return;

        if (isUpdate(action)) {
            updateListenersOnStatus(intent);
        } else if (isPublished(action)) {
            updateListenersOnCompletion(intent);
        }
    }

    private boolean isPublished(String action) {
        return action.equals(SocialPublishingService.PUBLISHING_COMPLETED);
    }

    private boolean isUpdate(String action) {
        return action.equals(SocialPublishingService.STATUS_UPDATE);
    }

    private void updateListenersOnCompletion(Intent intent) {
        ArrayList<PublishResults> ok
                = intent.getParcelableArrayListExtra(SocialPublishingService.PUBLISH_OK);
        ArrayList<PublishResults> notOk
                = intent.getParcelableArrayListExtra(SocialPublishingService.PUBLISH_NOT_OK);
        CallbackMessage result = intent.getParcelableExtra(SocialPublishingService.RESULT);

        if (result.isError()) {
            notifyPublishingFailed(ok, notOk, result);
        } else {
            notifyPublishingSucceeded(ok, notOk, result);
        }
    }

    private void notifyPublishingSucceeded(ArrayList<PublishResults> ok, ArrayList<PublishResults
            > notOk, CallbackMessage result) {
        for (SocialUploader.Listener listener : this) {
            listener.onPublishingCompleted(mReviewId, ok, notOk, result);
        }
    }

    private void notifyPublishingFailed(ArrayList<PublishResults> ok, ArrayList<PublishResults>
            notOk, CallbackMessage result) {
        ArrayList<String> platforms = new ArrayList<>();
        for (PublishResults results : ok) {
            platforms.add(results.getPublisherName());
        }

        for (PublishResults results : notOk) {
            platforms.add(results.getPublisherName());
        }

        for (SocialUploader.Listener listener : this) {
            listener.onPublishingFailed(mReviewId, platforms, result);
        }
    }

    private void updateListenersOnStatus(Intent intent) {
        double percentage = intent.getDoubleExtra(SocialPublishingService.STATUS_PERCENTAGE, 0.);
        PublishResults results = intent.getParcelableExtra(SocialPublishingService.STATUS_RESULTS);
        for (SocialUploader.Listener listener : this) {
            listener.onPublishingStatus(mReviewId, percentage, results);
        }
    }
}
