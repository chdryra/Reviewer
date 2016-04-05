/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.SocialUploader;

import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BroadcastingService;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPublishingListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformsPublisherAndroid extends
        BroadcastingService<SocialPublishingService, SocialPublishingReceiver, SocialPublishingListener>
        implements SocialPlatformsPublisher {

    private ArrayList<String> mPlatformNames;
    private ReviewId mId;

    public SocialPlatformsPublisherAndroid(Context context,
                                           ArrayList<String> platformNames,
                                           SocialPublishingReceiver receiver) {
        super(context, SocialPublishingService.class, receiver);
        mPlatformNames = platformNames;
        mId = receiver.getReviewId();
        registerReceiverAction(SocialPublishingService.STATUS_UPDATE);
        registerReceiverAction(SocialPublishingService.PUBLISHING_COMPLETED);
    }

    @Override
    public void publishReview() {
        Intent service = newService();
        service.putExtra(PublishingAction.PUBLISHED, mId.toString());
        service.putStringArrayListExtra(PublishingAction.PLATFORMS, mPlatformNames);

        startService(service);
    }
}
