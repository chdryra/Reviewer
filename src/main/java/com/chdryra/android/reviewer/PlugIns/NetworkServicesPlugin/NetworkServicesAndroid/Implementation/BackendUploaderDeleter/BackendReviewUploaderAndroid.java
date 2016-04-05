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
        BroadcastingService<BackendRepoService, ReviewUploaderReceiver, ReviewUploaderListener>
        implements BackendReviewUploader {

    private ReviewId mId;

    public BackendReviewUploaderAndroid(Context context, ReviewUploaderReceiver receiver) {
        super(context, BackendRepoService.class, receiver);
        mId = receiver.getReviewId();
        registerReceiverAction(BackendRepoService.UPLOAD_COMPLETED);
    }

    @Override
    public void publishReview() {
        Intent service = newService();
        service.putExtra(BackendRepoService.REVIEW_ID, mId.toString());
        service.putExtra(BackendRepoService.REQUEST_SERVICE, BackendRepoService.Service.UPLOAD);

        startService(service);
    }
}
