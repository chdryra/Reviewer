/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Interfaces;

import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.NetworkPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface SocialPlatformsPublisher extends NetworkPublisher<SocialPublishingListener>{
    @Override
    void registerListener(SocialPublishingListener socialPublishingListener);

    @Override
    void unregisterListener(SocialPublishingListener socialPublishingListener);

    @Override
    void publishReview();
}
