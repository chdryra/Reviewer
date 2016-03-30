/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Plugin;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactoryBackendUploader;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BackendUploader.BackendReviewUploaderAndroid;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BackendUploader.ReviewUploaderReceiver;
import com.chdryra.android.reviewer.NetworkServices.Backend.BackendReviewUploader;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBackendUploaderService implements FactoryBackendUploader {
    private static final ReviewUploaderReceiver RECEIVER = new ReviewUploaderReceiver();
    private final BackendReviewUploaderAndroid mUploader;

    public FactoryBackendUploaderService(Context context) {
        mUploader = new BackendReviewUploaderAndroid(context, RECEIVER);
    }

    @Override
    public BackendReviewUploader getUploader() {
        return mUploader;
    }
}
