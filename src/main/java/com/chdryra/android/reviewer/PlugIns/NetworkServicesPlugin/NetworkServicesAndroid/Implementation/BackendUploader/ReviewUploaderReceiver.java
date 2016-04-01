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

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.Utils.CallbackMessage;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.ReviewUploaderListener;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BroadcastingServiceReceiver;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderReceiver extends BroadcastingServiceReceiver<ReviewUploaderListener> {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String id = intent.getStringExtra(ReviewUploaderService.REVIEW_ID);
        CallbackMessage result = intent.getParcelableExtra(ReviewUploaderService.RESULT);

        DatumReviewId reviewId = new DatumReviewId(id);
        for (ReviewUploaderListener listener : this) {
            if (action.equals(ReviewUploaderService.UPLOAD_COMPLETED)) {
                listener.onUploadedToBackend(reviewId, result);
            } else if (action.equals(ReviewUploaderService.DELETE_COMPLETED)){
                listener.onDeletedFromBackend(reviewId, result);
            }
        }
    }
}
