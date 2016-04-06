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

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BroadcastingService;
import com.chdryra.android.reviewer.NetworkServices.Backend.BackendReviewUploader;
import com.chdryra.android.reviewer.NetworkServices.Backend.ReviewUploaderListener;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewUploaderAndroid extends
        BroadcastingService<BackendRepositoryService, ReviewUploaderReceiver, ReviewUploaderListener>
        implements BackendReviewUploader {

    private ReviewId mId;

    public BackendReviewUploaderAndroid(Context context, ReviewUploaderReceiver receiver) {
        super(context, BackendRepositoryService.class, receiver);
        mId = receiver.getReviewId();
        registerReceiverAction(BackendRepositoryService.UPLOAD_COMPLETED);
    }

    @Override
    public void publishReview() {
        Intent service = newService();
        service.putExtra(BackendRepositoryService.REVIEW_ID, mId.toString());
        service.putExtra(BackendRepositoryService.REQUEST_SERVICE, BackendRepositoryService.Service.UPLOAD);

        startService(service);
    }
}
