/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api;

import android.content.Context;

import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.FactoryReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces
        .FactoryReviewUploader;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.FactorySocialPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface NetworkServicesPlugin {
    FactorySocialPublisher getSocialPublisherFactory(Context context);

    FactoryReviewUploader getBackendUploaderFactory(Context context);

    FactoryReviewDeleter getBackendDeleterFactory(Context context);
}
