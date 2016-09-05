/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Plugin;

import android.content.Context;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewUploader;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api.FactoryBackendUploader;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendService.ReviewUploaderAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BackendService.ReviewUploaderReceiver;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBackendUploaderService implements FactoryBackendUploader {
    private final Context mContext;

    public FactoryBackendUploaderService(Context context) {
        mContext = context;
    }

    @Override
    public ReviewUploader newUploader(ReviewId id) {
        return new ReviewUploaderAndroid(mContext, new ReviewUploaderReceiver(id));
    }
}
