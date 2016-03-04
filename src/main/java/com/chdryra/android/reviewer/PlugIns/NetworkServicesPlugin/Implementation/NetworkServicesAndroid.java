/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactoryReviewUploader;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NetworkServicesAndroid implements NetworkServicesPlugin {
    private Context mContext;
    private final FactoryReviewUploaderService mFactory;

    public NetworkServicesAndroid(Context context) {
        mFactory = new FactoryReviewUploaderService(context);
    }

    @Override
    public FactoryReviewUploader getUploaderFactory(Context context) {
        return mFactory;
    }
}
