/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkStoreCallback;
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkerToken;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.UploadListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisherListener;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewPublisherImpl implements ReviewPublisher, UploadListener,
        SocialPublisher.Listener, WorkStoreCallback<Review> {
    private final ReviewQueue mQueue;
    private final SocialConsumer mSocial;

    private final ArrayList<ReviewPublisherListener> mListeners;
    private final ArrayList<ReviewId> mUploadComplete;
    private final ArrayList<ReviewId> mSocialComplete;

    private final Map<Review, QueueCallback> mAddCallbacks;
    private final Map<ReviewId, QueueCallback> mGetCallbacks;

    public ReviewPublisherImpl(ReviewQueue queue, BackendConsumer backend, SocialConsumer social) {
        mQueue = queue;

        backend.setQueue(mQueue);
        backend.registerListener(this);

        mSocial = social;
        mSocial.setQueue(mQueue);
        mSocial.registerListener(this);

        mListeners = new ArrayList<>();
        mUploadComplete = new ArrayList<>();
        mSocialComplete = new ArrayList<>();

        mAddCallbacks = new HashMap<>();
        mGetCallbacks = new HashMap<>();
    }

    @Override
    public synchronized void addToQueue(Review review, ArrayList<String> platforms, QueueCallback
            callback) {
        mAddCallbacks.put(review, callback);
        mSocial.setPlatforms(review.getReviewId(), platforms);
        mQueue.addReviewToQueue(review, this);
    }

    @Override
    public synchronized WorkerToken getFromQueue(ReviewId reviewId, QueueCallback callback,
                                                 Object publishWorker) {
        mGetCallbacks.put(reviewId, callback);
        return mQueue.getItemForWork(reviewId.toString(), this, publishWorker);
    }

    @Override
    public void workComplete(WorkerToken token) {
        mQueue.workComplete(token);
    }

    @Override
    public void registerListener(ReviewPublisherListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    @Override
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
    public void onPublishingFailed(ReviewId reviewId, Collection<String> platforms, CallbackMessage
            result) {
        notifyListenersOnPublishingFailed(reviewId, platforms, result);
    }

    @Override
    public void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults justUploaded) {
        notifyListenersOnPublishingStatus(reviewId, percentage, justUploaded);
    }

    @Override
    public void onPublishingCompleted(ReviewId reviewId,
                                      Collection<PublishResults> publishedOk,
                                      Collection<PublishResults> publishedNotOk,
                                      CallbackMessage result) {
        mSocialComplete.add(reviewId);
        removeFromQueueIfPublishingComplete(reviewId);
        notifyListenersOnPublishingCompleted(reviewId, publishedOk, publishedNotOk, result);
    }

    @Override
    public void onAddedToStore(Review item, String storeId, CallbackMessage result) {
        QueueCallback callback = mAddCallbacks.remove(item);
        callback.onAddedToQueue(item.getReviewId(), result);
    }

    @Override
    public void onRetrievedFromStore(Review item, String requestedId, CallbackMessage result) {
        QueueCallback callback = mGetCallbacks.remove(item.getReviewId());
        callback.onRetrievedFromQueue(item, result);
    }

    @Override
    public void onRemovedFromStore(String itemId, CallbackMessage result) {

    }

    @Override
    public void onFailed(@Nullable Review item, @Nullable String itemId, CallbackMessage result) {
        if (item != null) {
            //Failed onAdd...
            QueueCallback callback = mAddCallbacks.remove(item);
            mSocial.unsetPlatforms(item.getReviewId());
            callback.onFailed(item, reviewId(itemId), result);
        } else if(itemId != null){
            //Failed onRetrieved...
            QueueCallback callback = mGetCallbacks.remove(reviewId(itemId));
            callback.onFailed(null, reviewId(itemId), result);
        }
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

    private void notifyListenersOnPublishingStatus(ReviewId id, double percentage, PublishResults justUploaded) {
        for (ReviewPublisherListener listener : mListeners) {
            listener.onPublishingStatus(id, percentage, justUploaded);
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
