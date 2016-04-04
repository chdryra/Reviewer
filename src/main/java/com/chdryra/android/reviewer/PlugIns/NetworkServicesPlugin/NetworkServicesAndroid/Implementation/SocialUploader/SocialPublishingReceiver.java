/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.SocialUploader;


import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPublishingListener;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BroadcastingServiceReceiver;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPublishingReceiver extends
        BroadcastingServiceReceiver<SocialPublishingListener> {
    private ReviewId mReviewId;

    public SocialPublishingReceiver(ReviewId reviewId) {
        mReviewId = reviewId;
    }

    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
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

        for (SocialPublishingListener listener : this) {
            listener.onPublishCompleted(ok, notOk, result);
        }
    }

    private void updateListenersOnStatus(Intent intent) {
        double percentage = intent.getDoubleExtra(SocialPublishingService.STATUS_PERCENTAGE, 0.);
        PublishResults results = intent.getParcelableExtra(SocialPublishingService.STATUS_RESULTS);
        for (SocialPublishingListener listener : this) {
            listener.onPublishStatus(percentage, results);
        }
    }
}
