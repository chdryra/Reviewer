/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewBuilding.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishAction implements ReviewPublisher.QueueCallback {
    private final ReviewPublisher mPublisher;
    private final PublishCallback mResponse;

    public interface PublishCallback {
        void onQueuedToPublish(ReviewId id, CallbackMessage message);

        void onFailedToQueue(@Nullable Review review, @Nullable ReviewId id, CallbackMessage
                message);
    }

    public PublishAction(ReviewPublisher publisher, PublishCallback response) {
        mPublisher = publisher;
        mResponse = response;
    }

    public void publish(Review toPublish, ArrayList<String> selectedPublishers) {
        mPublisher.addToQueue(toPublish, selectedPublishers, this);
    }

    @Override
    public void onRetrievedFromQueue(Review review, CallbackMessage message) {
        throw new UnsupportedOperationException("Should never be called");
    }

    @Override
    public void onAddedToQueue(ReviewId id, CallbackMessage message) {
        mResponse.onQueuedToPublish(id, message);
    }

    @Override
    public void onFailed(@Nullable Review review, @Nullable ReviewId id, CallbackMessage message) {
        mResponse.onFailedToQueue(review, id, message);
    }
}
