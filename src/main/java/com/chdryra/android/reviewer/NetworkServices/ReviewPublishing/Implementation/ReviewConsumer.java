/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.AsyncWorkQueue;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkStoreCallback;
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkerToken;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewConsumer<Listener> implements AsyncWorkQueue.QueueObserver, WorkStoreCallback<Review> {
    private ReviewQueue mQueue;
    private Map<ReviewId, ReviewWorker> mWorkers;
    private ArrayList<ReviewId> mWorking;
    private ArrayList<Listener> mListeners;
    private Map<String, WorkerToken> mTokens;

    protected abstract void OnFailedToRetrieve(ReviewId reviewId, CallbackMessage result);

    protected abstract ReviewWorker newWorker(ReviewId reviewId);

    public interface ReviewWorker {
        void doWork(Review review);
    }

    public ReviewConsumer() {
        mWorkers = new HashMap<>();
        mWorking = new ArrayList<>();
        mListeners = new ArrayList<>();
        mTokens = new HashMap<>();
    }

    public void setQueue(ReviewQueue queue) {
        if(mQueue != null) throw new IllegalStateException("Cannot reset Queue!");

        mQueue = queue;
        mQueue.registerObserver(this);
    }

    protected ArrayList<Listener> getListeners() {
        return mListeners;
    }

    public void registerListener(Listener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    protected void onWorkCompleted(ReviewId reviewId) {
        mWorking.remove(reviewId);
        mWorkers.remove(reviewId);
        mQueue.workComplete(mTokens.get(reviewId.toString()));
    }

    @Override
    public void onAddedToQueue(String itemId) {
        mTokens.put(itemId, mQueue.getItemForWork(itemId, this, this));
    }

    @Override
    public void onAddedToStore(Review item, String storeId, CallbackMessage result) {

    }

    @Override
    public void onRetrievedFromStore(Review review, String requestedId, CallbackMessage result) {
        ReviewId reviewId = review.getReviewId();
        if (!mWorking.contains(reviewId)) {
            mWorking.add(reviewId);
            getWorker(reviewId).doWork(review);
        }
    }

    @Override
    public void onRemovedFromStore(String itemId, CallbackMessage result) {

    }

    @Override
    public void onFailed(@Nullable Review item, @Nullable String itemId, CallbackMessage result) {
        if( itemId!= null) OnFailedToRetrieve(new DatumReviewId(itemId), result);
    }

    @NonNull
    private ReviewWorker getWorker(ReviewId reviewId) {
        if (!mWorkers.containsKey(reviewId)) {
            ReviewWorker uploader = newWorker(reviewId);
            mWorkers.put(reviewId, uploader);
        }

        return mWorkers.get(reviewId);
    }
}
