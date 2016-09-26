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
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.SocialUploader;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api.FactorySocialPublisher;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.SocialPublisherService.SocialPublisherAndroid;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.SocialPublisherService.SocialPublishingReceiver;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactorySocialPublisherService implements FactorySocialPublisher {
    private final Context mContext;

    public FactorySocialPublisherService(Context context) {
        mContext = context;
    }

    @Override
    public SocialUploader newPublisher(ReviewId id, ArrayList<String> platformNames) {
        return new SocialPublisherAndroid(mContext, platformNames, new SocialPublishingReceiver(id));
    }
}
