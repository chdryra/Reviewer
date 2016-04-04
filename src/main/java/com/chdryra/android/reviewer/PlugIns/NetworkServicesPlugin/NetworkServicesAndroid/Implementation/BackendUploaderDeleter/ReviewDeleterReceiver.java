/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendUploaderDeleter;


import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.Backend.ReviewDeleterListener;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BroadcastingServiceReceiver;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDeleterReceiver extends BroadcastingServiceReceiver<ReviewDeleterListener> {
    private ReviewId mReviewId;

    public ReviewDeleterReceiver(ReviewId reviewId) {
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
        CallbackMessage result = intent.getParcelableExtra(BackendRepoService.RESULT);

        if(!mReviewId.equals(reviewId) || !isDownload(action)) return;

        for (ReviewDeleterListener listener : this) {
            listener.onDeletedFromBackend(reviewId, result);
        }
    }

    private boolean isDownload(String action) {
        return action.equals(BackendRepoService.DELETE_COMPLETED);
    }
}
