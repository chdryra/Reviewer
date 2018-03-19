/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid
        .Implementation.BackendService;


import android.content.Context;
import android.content.Intent;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid.Implementation.BroadcastingServiceReceiver;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class BackendRepoServiceReceiver<L> extends BroadcastingServiceReceiver<L>
        implements HasReviewId {
    private final String mAction;
    private final ReviewId mReviewId;

    protected abstract void notifyListener(L listener, DatumReviewId reviewId, CallbackMessage
            result);

    public BackendRepoServiceReceiver(String action, ReviewId reviewId) {
        mAction = action;
        mReviewId = reviewId;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String id = intent.getStringExtra(BackendRepoService.REVIEW_ID);
        DatumReviewId reviewId = new DatumReviewId(id);
        CallbackMessage result = intent.getParcelableExtra(BackendRepoService.RESULT);

        if (!mReviewId.equals(reviewId) || !action.equals(mAction)) return;

        for (L listener : this) {
            notifyListener(listener, reviewId, result);
        }
    }
}
