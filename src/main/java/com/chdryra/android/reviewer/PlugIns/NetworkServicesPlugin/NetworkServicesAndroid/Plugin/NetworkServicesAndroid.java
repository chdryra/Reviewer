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
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactorySocialUploader;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.BackendUploader
        .FactoryBackendUploaderService;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.SocialUploader
        .FactorySocialUploaderService;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NetworkServicesAndroid implements NetworkServicesPlugin {
    private final FactorySocialUploaderService mSocial;
    private final FactoryBackendUploaderService mBackend;

    public NetworkServicesAndroid(Context context) {
        mSocial = new FactorySocialUploaderService(context);
        mBackend = new FactoryBackendUploaderService(context);
    }

    @Override
    public FactorySocialUploader getSocialUploaderFactory(Context context) {
        return mSocial;
    }

    @Override
    public FactoryBackendUploader getBackendUploaderFactory(Context context) {
        return mBackend;
    }
}
