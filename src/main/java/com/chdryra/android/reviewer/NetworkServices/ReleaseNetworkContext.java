/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Factories.FactoryReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Factories.FactoryReviewPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseNetworkContext implements NetworkContext {
    private final FactoryReviewPublisher mPublisherFactory;
    private final FactoryReviewDeleter mDeleterFactory;

    public ReleaseNetworkContext(Context context, NetworkServicesPlugin plugin) {
        mPublisherFactory = new FactoryReviewPublisher(plugin.getBackendUploaderFactory(context),
                plugin.getSocialPublisherFactory(context));
        mDeleterFactory = new FactoryReviewDeleter(plugin.getBackendDeleterFactory(context));
    }

    @Override
    public FactoryReviewPublisher getPublisherFactory() {
        return mPublisherFactory;
    }

    @Override
    public FactoryReviewDeleter getDeleterFactory() {
        return mDeleterFactory;
    }
}
