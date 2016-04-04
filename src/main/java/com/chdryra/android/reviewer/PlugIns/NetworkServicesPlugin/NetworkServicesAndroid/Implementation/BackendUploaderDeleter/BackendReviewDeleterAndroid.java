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
import com.chdryra.android.reviewer.NetworkServices.Backend.BackendReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.Backend.ReviewDeleterListener;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BroadcastingService;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewDeleterAndroid extends
        BroadcastingService<BackendRepoService, ReviewDeleterReceiver, ReviewDeleterListener>
        implements BackendReviewDeleter {

    private ReviewId mId;

    public BackendReviewDeleterAndroid(Context context, ReviewDeleterReceiver receiver) {
        super(context, BackendRepoService.class, receiver);
        mId = receiver.getReviewId();
        registerReceiverAction(BackendRepoService.DELETE_COMPLETED);
    }

    @Override
    public void deleteReview() {
        Intent service = newService();
        service.putExtra(BackendRepoService.REVIEW_ID, mId.toString());
        service.putExtra(BackendRepoService.REQUEST_SERVICE, BackendRepoService.Service.DELETE);

        startService(service);
    }
}
