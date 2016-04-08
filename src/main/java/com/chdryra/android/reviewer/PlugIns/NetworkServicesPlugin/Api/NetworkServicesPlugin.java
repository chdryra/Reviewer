/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api;

import android.content.Context;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface NetworkServicesPlugin {
    FactorySocialPublisher getSocialPublisherFactory(Context context);

    FactoryBackendUploader getBackendUploaderFactory(Context context);

    FactoryBackendDeleter getBackendDeleterFactory(Context context);
}
