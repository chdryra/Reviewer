/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BackendService;


import android.content.Context;
import android.content.Intent;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BroadcastingService;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BroadcastingServiceReceiver;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
class BackendReviewAndroid<R extends BroadcastingServiceReceiver<L> & HasReviewId, L> extends
        BroadcastingService<BackendRepoService, R, L> {

    private final ReviewId mId;
    private final BackendRepoService.Service mService;

    BackendReviewAndroid(Context context, R receiver, BackendRepoService.Service service) {
        super(context, BackendRepoService.class, receiver);
        mId = receiver.getReviewId();
        mService = service;
        registerReceiverAction(mService.completed());
    }

    void startService() {
        Intent service = newService();
        service.putExtra(BackendRepoService.REVIEW_ID, mId.toString());
        service.putExtra(BackendRepoService.REQUEST_SERVICE, mService);

        startService(service);
    }
}
