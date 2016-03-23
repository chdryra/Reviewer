/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BackendUploader;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BroadcastingServiceReceiver;
import com.chdryra.android.reviewer.Social.Implementation.ReviewUploaderError;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewUploaderListener;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderReceiver extends BroadcastingServiceReceiver<ReviewUploaderListener> {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ReviewUploaderService.UPLOAD_COMPLETED)) {
            updateListenersOnCompletion(intent);
        } else if (action.equals(ReviewUploaderService.DELETE_COMPLETED)) {
            updateListenersOnDeletion(intent);
        }
    }

    private void updateListenersOnCompletion(Intent intent) {
        String id = intent.getStringExtra(ReviewUploaderService.REVIEW_ID);
        ReviewUploaderError error = getReviewUploaderError(intent);

        for (ReviewUploaderListener listener : this) {
            listener.onReviewUploaded(new DatumReviewId(id), error);
        }
    }

    private void updateListenersOnDeletion(Intent intent) {
        String id = intent.getStringExtra(ReviewUploaderService.REVIEW_ID);
        ReviewUploaderError error = getReviewUploaderError(intent);

        for (ReviewUploaderListener listener : this) {
            listener.onReviewDeleted(new DatumReviewId(id), error);
        }
    }

    @NonNull
    private ReviewUploaderError getReviewUploaderError(Intent intent) {
        String errorMessage = intent.getStringExtra(ReviewUploaderService.ERROR);
        ReviewUploaderError error = ReviewUploaderError.none();
        if (errorMessage != null) error = ReviewUploaderError.error(errorMessage);
        return error;
    }
}
