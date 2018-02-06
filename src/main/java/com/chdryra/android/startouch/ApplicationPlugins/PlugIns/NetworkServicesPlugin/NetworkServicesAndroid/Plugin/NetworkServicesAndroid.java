/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Plugin;


import android.content.Context;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api
        .NetworkServicesPlugin;
import com.chdryra.android.startouch.NetworkServices.ReviewDeleting.FactoryReviewDeleter;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces
        .FactoryReviewUploader;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces
        .FactorySocialPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NetworkServicesAndroid implements NetworkServicesPlugin {
    private final FactorySocialPublisherService mSocialPublisherFactory;
    private final FactoryReviewUploaderService mUploaderfactory;
    private final FactoryBackendDeleterService mDeleterFactory;

    public NetworkServicesAndroid(Context context) {
        mSocialPublisherFactory = new FactorySocialPublisherService(context);
        mUploaderfactory = new FactoryReviewUploaderService(context);
        mDeleterFactory = new FactoryBackendDeleterService(context);
    }

    @Override
    public FactorySocialPublisher getSocialPublisherFactory(Context context) {
        return mSocialPublisherFactory;
    }

    @Override
    public FactoryReviewUploader getBackendUploaderFactory(Context context) {
        return mUploaderfactory;
    }

    @Override
    public FactoryReviewDeleter getBackendDeleterFactory(Context context) {
        return mDeleterFactory;
    }
}
