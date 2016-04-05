/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces
        .BackendConsumerListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisherListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.SocialConsumerListener;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.WorkStoreCallback;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.WorkerToken;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewPublisher implements WorkStoreCallback<Review>, BackendConsumerListener,
        SocialConsumerListener {
    private ReviewQueue mQueue;
    private BackendConsumer mBackend;
    private SocialConsumer mSocial;
    private ArrayList<Review> mToQueue;
    private ArrayList<ReviewPublisherListener> mListeners;
    private ArrayList<ReviewId> mUploadComplete;
    private ArrayList<ReviewId> mSocialComplete;

    public ReviewPublisher(ReviewQueue queue, BackendConsumer backend, SocialConsumer social) {
        mQueue = queue;

        mBackend = backend;
        mBackend.setQueue(mQueue);
        mBackend.registerListener(this);
        mSocial = social;
        mSocial.setQueue(mQueue);
        mSocial.registerListener(this);

        mToQueue = new ArrayList<>();
        mListeners = new ArrayList<>();
        mUploadComplete = new ArrayList<>();
        mSocialComplete = new ArrayList<>();
    }

    public void addToQueue(Review review, ArrayList<String> socialPlatforms) {
        mToQueue.add(review);
        mSocial.setPlatforms(review.getReviewId(), socialPlatforms);
        queueToQueue();
    }

    public void registerListener(ReviewPublisherListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(ReviewPublisherListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    public WorkerToken getFromQueue(ReviewId reviewId, WorkStoreCallback<Review> callback, Object worker) {
        return mQueue.getItemForWork(reviewId.toString(), callback, worker);
    }

    @Override
    public void onUploadCompleted(ReviewId id, CallbackMessage result) {
        mUploadComplete.add(id);
        removeFromQueueIfPublishingComplete(id);
        notifyListenersOnUploadCompleted(id, result);
    }

    @Override
    public void onUploadFailed(ReviewId id, CallbackMessage result) {
        notifyListenersOnUploadFailed(id, result);
    }

    @Override
    public void onPublishingFailed(ReviewId id, Collection<String> platforms, CallbackMessage
            result) {
        notifyListenersOnPublishingFailed(id, platforms, result);
    }

    @Override
    public void onPublishingCompleted(ReviewId id,
                                      Collection<PublishResults> publishedOk,
                                      Collection<PublishResults> publishedNotOk,
                                      CallbackMessage result) {
        mSocialComplete.add(id);
        removeFromQueueIfPublishingComplete(id);
        notifyListenersOnPublishingCompleted(id, publishedOk, publishedNotOk, result);
    }


    @Override
    public void onAddedToStore(Review item, String storeId, CallbackMessage result) {
        if (!result.isError()) mToQueue.remove(item);
    }

    @Override
    public void onRetrievedFromStore(@Nullable Review item, String requestedId, CallbackMessage
            result) {

    }

    @Override
    public void onRemovedFromStore(String itemId, CallbackMessage result) {

    }

    @Override
    public void onFailed(@Nullable Review item, @Nullable String itemId, CallbackMessage result) {

    }

    private void queueToQueue() {
        for (Review review : mToQueue) {
            mQueue.addForWork(review, this);
        }
    }

    private void removeFromQueueIfPublishingComplete(ReviewId id) {
        if (mUploadComplete.contains(id) && mSocialComplete.contains(id)) {
            mQueue.removeItemOnWorkCompleted(id.toString());
        }
    }


    private void notifyListenersOnUploadCompleted(ReviewId id, CallbackMessage result) {
        for (ReviewPublisherListener listener : mListeners) {
            listener.onUploadCompleted(id, result);
        }
    }

    private void notifyListenersOnUploadFailed(ReviewId id, CallbackMessage result) {
        for (ReviewPublisherListener listener : mListeners) {
            listener.onUploadFailed(id, result);
        }
    }

    private void notifyListenersOnPublishingFailed(ReviewId id, Collection<String> platforms,
                                                   CallbackMessage result) {
        for (ReviewPublisherListener listener : mListeners) {
            listener.onPublishingFailed(id, platforms, result);
        }
    }

    private void notifyListenersOnPublishingCompleted(ReviewId id,
                                                      Collection<PublishResults> publishedOk,
                                                      Collection<PublishResults> publishedNotOk,
                                                      CallbackMessage result) {
        for (ReviewPublisherListener listener : mListeners) {
            listener.onPublishingCompleted(id, publishedOk, publishedNotOk, result);
        }
    }
}
