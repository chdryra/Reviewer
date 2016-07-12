/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Factories;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api
        .FactoryBackendUploader;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api
        .FactorySocialPublisher;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation.BackendConsumer;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation
        .ReviewPublisherImpl;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation.ReviewQueue;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation.ReviewStore;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation.SocialConsumer;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Interfaces.LocalRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewPublisher {
    FactoryBackendUploader mUploaderFactory;
    FactorySocialPublisher mPublisherFactory;

    public FactoryReviewPublisher(FactoryBackendUploader uploaderFactory,
                                  FactorySocialPublisher publisherFactory) {
        mUploaderFactory = uploaderFactory;
        mPublisherFactory = publisherFactory;
    }

    public ReviewPublisher newPublisher(LocalRepository repo) {
        ReviewQueue queue = new ReviewQueue(new ReviewStore(repo));
        BackendConsumer backend = new BackendConsumer(mUploaderFactory);
        SocialConsumer social = new SocialConsumer(mPublisherFactory);
        return new ReviewPublisherImpl(queue, backend, social);
    }
}
