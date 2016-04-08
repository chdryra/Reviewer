/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendUploaderDeleter;


import android.content.Context;

import com.chdryra.android.reviewer.NetworkServices.Backend.BackendReviewUploader;
import com.chdryra.android.reviewer.NetworkServices.Backend.ReviewUploaderListener;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewUploaderAndroid
        extends BackendReviewAndroid<ReviewUploaderReceiver, ReviewUploaderListener>
        implements BackendReviewUploader {

    public BackendReviewUploaderAndroid(Context context, ReviewUploaderReceiver receiver) {
        super(context, receiver, BackendRepoService.Service.UPLOAD);
    }

    @Override
    public void publishReview() {
        startService();
    }
}
