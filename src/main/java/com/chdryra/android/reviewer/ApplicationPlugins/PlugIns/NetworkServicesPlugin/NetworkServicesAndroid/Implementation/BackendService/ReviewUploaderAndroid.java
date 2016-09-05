/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendService;


import android.content.Context;

import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewUploader;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderAndroid
        extends BackendReviewAndroid<ReviewUploaderReceiver, ReviewUploader.Listener>
        implements ReviewUploader {

    public ReviewUploaderAndroid(Context context, ReviewUploaderReceiver receiver) {
        super(context, receiver, BackendRepoService.Service.UPLOAD);
    }

    @Override
    public void publishReview() {
        startService();
    }
}
