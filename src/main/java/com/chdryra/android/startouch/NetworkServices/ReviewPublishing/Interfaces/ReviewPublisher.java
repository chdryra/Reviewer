/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.AsyncUtils.WorkerToken;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewPublisher {
    interface QueueCallback {
        void onAddedToQueue(ReviewId id, CallbackMessage message);

        void onRetrievedFromQueue(Review review, CallbackMessage message);

        void onFailed(@Nullable Review review, @Nullable ReviewId id, CallbackMessage message);
    }

    void addToQueue(Review review, ArrayList<String> platforms, QueueCallback callback);

    WorkerToken getFromQueue(ReviewId reviewId, QueueCallback callback, Object publishWorker);

    void workComplete(WorkerToken token);

    void registerListener(ReviewPublisherListener listener);

    void unregisterListener(ReviewPublisherListener listener);
}
