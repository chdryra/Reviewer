/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.SocialPublisherService;

import android.content.Context;
import android.content.Intent;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BroadcastingService;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.SocialUploader;
import com.chdryra.android.startouch.Social.Implementation.PublishingAction;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPublisherAndroid extends
        BroadcastingService<SocialPublishingService, SocialPublishingReceiver, SocialUploader
                .Listener>
        implements SocialUploader {

    private final ArrayList<String> mPlatformNames;
    private final ReviewId mId;

    public SocialPublisherAndroid(Context context,
                                  ArrayList<String> platformNames,
                                  SocialPublishingReceiver receiver) {
        super(context, SocialPublishingService.class, receiver);
        mPlatformNames = platformNames;
        mId = receiver.getReviewId();
        registerReceiverAction(SocialPublishingService.STATUS_UPDATE);
        registerReceiverAction(SocialPublishingService.PUBLISHING_COMPLETED);
    }

    @Override
    public void uploadReview() {
        Intent service = newService();
        service.putExtra(PublishingAction.PUBLISHED, mId.toString());
        service.putStringArrayListExtra(PublishingAction.PLATFORMS, mPlatformNames);

        startService(service);
    }
}
