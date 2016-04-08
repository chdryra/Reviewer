/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Plugin;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactoryBackendDeleter;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactoryBackendUploader;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactorySocialPublisher;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NetworkServicesAndroid implements NetworkServicesPlugin {
    private final FactorySocialPublisherService mSocialPublisherFactory;
    private final FactoryBackendUploaderService mUploaderfactory;
    private final FactoryBackendDeleterService mDeleterFactory;

    public NetworkServicesAndroid(Context context) {
        mSocialPublisherFactory = new FactorySocialPublisherService(context);
        mUploaderfactory = new FactoryBackendUploaderService(context);
        mDeleterFactory = new FactoryBackendDeleterService(context);
    }

    @Override
    public FactorySocialPublisher getSocialPublisherFactory(Context context) {
        return mSocialPublisherFactory;
    }

    @Override
    public FactoryBackendUploader getBackendUploaderFactory(Context context) {
        return mUploaderfactory;
    }

    @Override
    public FactoryBackendDeleter getBackendDeleterFactory(Context context) {
        return mDeleterFactory;
    }
}
