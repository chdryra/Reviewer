/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendService;



import android.content.Context;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleter;
import com.chdryra.android.reviewer.NetworkServices.ReviewDeleting.ReviewDeleterCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewDeleterAndroid
        extends BackendReviewAndroid<ReviewDeleterReceiver, ReviewDeleterCallback>
        implements ReviewDeleter, ReviewDeleterCallback {
    private ReviewDeleterCallback mCallback;

    public BackendReviewDeleterAndroid(Context context, ReviewDeleterReceiver receiver) {
        super(context, receiver, BackendRepoService.Service.DELETE);
    }

    @Override
    public void deleteReview(ReviewDeleterCallback callback) {
        mCallback = callback;
        registerListener(this);
        startService();
    }

    @Override
    public void onReviewDeleted(ReviewId reviewId, CallbackMessage result) {
        unregisterListener(this);
        mCallback.onReviewDeleted(reviewId, result);
    }
}
