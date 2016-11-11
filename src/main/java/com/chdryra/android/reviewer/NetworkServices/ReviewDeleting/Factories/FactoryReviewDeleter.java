/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Factories;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api.FactoryBackendDeleter;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.Interfaces.ReviewDeleter;

/**
 * Created by: Rizwan Choudrey
 * On: 11/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewDeleter {
    private final FactoryBackendDeleter mBackendDeleter;

    public FactoryReviewDeleter(FactoryBackendDeleter backendDeleter) {
        mBackendDeleter = backendDeleter;
    }

    public ReviewDeleter newDeleter(ReviewId id) {
        return mBackendDeleter.newDeleter(id);
    }
}
