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
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendUploader.BackendReviewUploaderAndroid;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendUploader.ReviewUploaderReceiver;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBackendUploaderService implements FactoryBackendUploader {
    private Context mContext;

    public FactoryBackendUploaderService(Context context) {
        mContext = context;
    }

    @Override
    public com.chdryra.android.reviewer.Social.Interfaces.BackendReviewUploader newUploader() {
        return new BackendReviewUploaderAndroid(mContext, new ReviewUploaderReceiver());
    }
}
