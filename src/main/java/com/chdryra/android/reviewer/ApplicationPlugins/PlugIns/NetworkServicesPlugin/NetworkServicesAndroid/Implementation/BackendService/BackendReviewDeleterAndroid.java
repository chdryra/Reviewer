/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendService;



import android.content.Context;

import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleterListener;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewDeleterAndroid
        extends BackendReviewAndroid<ReviewDeleterReceiver, ReviewDeleterListener>
        implements ReviewDeleter {

    public BackendReviewDeleterAndroid(Context context, ReviewDeleterReceiver receiver) {
        super(context, receiver, BackendRepoService.Service.DELETE);
    }

    @Override
    public void deleteReview() {
        startService();
    }
}
