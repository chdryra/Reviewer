/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Plugin;


import android.content.Context;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BackendService.BackendReviewDeleterAndroid;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BackendService.ReviewDeleterReceiver;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.NetworkServices.ReviewDeleting.FactoryReviewDeleter;
import com.chdryra.android.startouch.NetworkServices.ReviewDeleting.ReviewDeleter;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryBackendDeleterService implements FactoryReviewDeleter {
    private final Context mContext;

    public FactoryBackendDeleterService(Context context) {
        mContext = context;
    }

    @Override
    public ReviewDeleter newDeleter(ReviewId id) {
        return new BackendReviewDeleterAndroid(mContext, new ReviewDeleterReceiver(id));
    }
}
