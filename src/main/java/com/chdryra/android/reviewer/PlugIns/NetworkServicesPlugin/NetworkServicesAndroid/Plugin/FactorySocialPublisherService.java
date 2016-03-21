/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Plugin;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactorySocialPublisher;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.SocialUploader.SocialPlatformsPublisherAndroid;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.SocialUploader.SocialPublishingReceiver;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactorySocialPublisherService implements FactorySocialPublisher {
    private Context mContext;

    public FactorySocialPublisherService(Context context) {
        mContext = context;
    }

    @Override
    public SocialPlatformsPublisher newPublisher() {
        return new SocialPlatformsPublisherAndroid(mContext, new SocialPublishingReceiver());
    }
}
