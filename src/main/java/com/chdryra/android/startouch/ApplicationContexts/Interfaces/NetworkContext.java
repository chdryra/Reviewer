/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationContexts.Interfaces;


import com.chdryra.android.startouch.NetworkServices.ReviewDeleting.FactoryReviewDeleter;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Factories
        .FactoryReviewPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface NetworkContext {
    FactoryReviewPublisher getPublisherFactory();

    FactoryReviewDeleter getDeleterFactory();
}
