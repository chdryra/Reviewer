/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.BackendConsumerListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisherListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.SocialConsumerListener;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkStoreCallback;
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkerToken;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    private ArrayList<ReviewPublisherListener> mListeners;
    private ArrayList<ReviewId> mUploadComplete;
    private ArrayList<ReviewId> mSocialComplete;

    private Map<Review, QueueCallback> mAddCallbacks;
    private Map<ReviewId, QueueCallback> mGetCallbacks;

    public interface QueueCallback {
        void onAddedToQueue(ReviewId id, CallbackMessage message);

        void onRetrievedFromQueue(Review review, CallbackMessage message);

        void onFailed(@Nullable Review review, @Nullable ReviewId id, CallbackMessage message);
    }

    public ReviewPublisher(ReviewQueue queue, BackendConsumer backend, SocialConsumer social) {
        mQueue = queue;

        mBackend = backend;
        mBackend.setQueue(mQueue);
        mBackend.registerListener(this);
        mSocial = social;
        mSocial.setQueue(mQueue);
        mSocial.registerListener(this);

        mListeners = new ArrayList<>();
        mUploadComplete = new ArrayList<>();
        mSocialComplete = new ArrayList<>();

        mAddCallbacks = new HashMap<>();
        mGetCallbacks = new HashMap<>();
    }

    public synchronized void addToQueue(Review review, ArrayList<String> platforms, QueueCallback
            callback) {
        mAddCallbacks.put(review, callback);
        mSocial.setPlatforms(review.getReviewId(), platforms);
        mQueue.addForWork(review, this);
    }

    public synchronized WorkerToken getFromQueue(ReviewId reviewId, QueueCallback callback,
                                                 Object worker) {
        mGetCallbacks.put(reviewId, callback);
        return mQueue.getItemForWork(reviewId.toString(), this, worker);
    }

    public void workComplete(WorkerToken token) {
        mQueue.workComplete(token);
    }

    public void registerListener(ReviewPublisherListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(ReviewPublisherListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
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
        QueueCallback callback = mAddCallbacks.remove(item);
        if (!result.isError()) {
            callback.onAddedToQueue(item.getReviewId(), result);
        } else {
            callback.onFailed(item, reviewId(storeId), result);
        }
    }

    @Override
    public void onRetrievedFromStore(Review item, String requestedId, CallbackMessage result) {
        QueueCallback callback = mGetCallbacks.remove(item.getReviewId());
        if (!result.isError()) {
            callback.onRetrievedFromQueue(item, result);
        } else {
            callback.onFailed(item, reviewId(requestedId), result);
        }
    }

    @Override
    public void onRemovedFromStore(String itemId, CallbackMessage result) {

    }

    @Override
    public void onFailed(@Nullable Review item, @Nullable String itemId, CallbackMessage result) {

    }

    @Nullable
    private DatumReviewId reviewId(@Nullable String storeId) {
        return storeId != null ? new DatumReviewId(storeId) : null;
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
