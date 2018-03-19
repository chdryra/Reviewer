/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Factories;

import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Implementation
        .BackendConsumer;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Implementation
        .ReviewPublisherImpl;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Implementation.ReviewQueue;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Implementation.ReviewStore;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Implementation.SocialConsumer;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces
        .FactoryReviewUploader;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces
        .FactorySocialPublisher;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewPublisher {
    private final FactoryReviewUploader mUploaderFactory;
    private final FactorySocialPublisher mPublisherFactory;

    public FactoryReviewPublisher(FactoryReviewUploader uploaderFactory,
                                  FactorySocialPublisher publisherFactory) {
        mUploaderFactory = uploaderFactory;
        mPublisherFactory = publisherFactory;
    }

    public ReviewPublisher newPublisher(ReviewsRepoWriteable repo) {
        ReviewQueue queue = new ReviewQueue(new ReviewStore(repo));
        BackendConsumer backend = new BackendConsumer(mUploaderFactory);
        SocialConsumer social = new SocialConsumer(mPublisherFactory);
        return new ReviewPublisherImpl(queue, backend, social);
    }
}
