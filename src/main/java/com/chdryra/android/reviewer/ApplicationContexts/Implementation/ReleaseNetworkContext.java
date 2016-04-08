/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.NetworkContext;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Factories.FactoryReviewPublisher;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactoryBackendDeleter;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseNetworkContext implements NetworkContext {
    private FactoryReviewPublisher mPublisher;
    private FactoryBackendDeleter mDeleterFactory;

    public ReleaseNetworkContext(Context context, NetworkServicesPlugin plugin) {
        mPublisher = new FactoryReviewPublisher(plugin.getBackendUploaderFactory(context),
                plugin.getSocialPublisherFactory(context));
        mDeleterFactory = plugin.getBackendDeleterFactory(context);
    }

    @Override
    public FactoryReviewPublisher getPublisherFactory() {
        return mPublisher;
    }

    @Override
    public FactoryBackendDeleter getDeleterFactory() {
        return mDeleterFactory;
    }
}
