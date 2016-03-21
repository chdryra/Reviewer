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
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactorySocialPublisher;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NetworkServicesAndroid implements NetworkServicesPlugin {
    private final FactorySocialPublisherService mSocial;
    private final FactoryBackendUploaderService mBackend;

    public NetworkServicesAndroid(Context context) {
        mSocial = new FactorySocialPublisherService(context);
        mBackend = new FactoryBackendUploaderService(context);
    }

    @Override
    public FactorySocialPublisher getSocialUploaderFactory(Context context) {
        return mSocial;
    }

    @Override
    public FactoryBackendUploader getBackendUploaderFactory(Context context) {
        return mBackend;
    }
}
