/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendUploader;


import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BroadcastingService;
import com.chdryra.android.reviewer.NetworkServices.Backend.BackendReviewUploader;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.ReviewUploaderListener;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewUploaderAndroid extends
        BroadcastingService<ReviewUploaderService, ReviewUploaderReceiver, ReviewUploaderListener>
        implements BackendReviewUploader {

    public BackendReviewUploaderAndroid(Context context, ReviewUploaderReceiver receiver) {
        super(context, ReviewUploaderService.class, receiver);
        registerReceiverAction(ReviewUploaderService.UPLOAD_COMPLETED);
        registerReceiverAction(ReviewUploaderService.DELETE_COMPLETED);
    }

    @Override
    public void uploadReview(ReviewId reviewId) {
        requestService(reviewId, ReviewUploaderService.Service.UPLOAD);
    }

    @Override
    public void deleteReview(ReviewId reviewId) {
        requestService(reviewId, ReviewUploaderService.Service.DELETE);
    }

    private void requestService(ReviewId reviewId, ReviewUploaderService.Service serviceType) {
        Intent service = newService();
        service.putExtra(ReviewUploaderService.REVIEW_ID, reviewId.toString());
        service.putExtra(ReviewUploaderService.REQUEST_SERVICE, serviceType);

        startService();
    }
}
