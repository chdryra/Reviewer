/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.Backend.ReviewUploaderListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.BackendConsumerListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.NetworkPublisher;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.AsyncWorkQueue;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.WorkerToken;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactoryBackendUploader;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendConsumer implements ReviewUploaderListener, AsyncWorkQueue.QueueObserver {
    private ReviewQueue mQueue;
    private FactoryBackendUploader mUploaderFactory;
    private Map<ReviewId, NetworkPublisher<ReviewUploaderListener>> mUploaders;
    private ArrayList<ReviewId> mUploading;
    private ArrayList<BackendConsumerListener> mListeners;
    private Map<String, WorkerToken> mTokens;

    public BackendConsumer(FactoryBackendUploader uploaderFactory) {
        mUploaderFactory = uploaderFactory;

        mUploaders = new HashMap<>();
        mUploading = new ArrayList<>();
        mListeners = new ArrayList<>();
        mTokens = new HashMap<>();
    }

    public void setQueue(ReviewQueue queue) {
        if(mQueue != null) throw new IllegalStateException("Cannot reset Queue!");

        mQueue = queue;
        mQueue.registerObserver(this);
    }

    public void registerListener(BackendConsumerListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(BackendConsumerListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    public void onUploadedToBackend(ReviewId reviewId, CallbackMessage result) {
        mUploading.remove(reviewId);
        if (result.isError()) {
            notifyListenersOnFail(reviewId, result);
            return;
        }

        notifyListenersOnSuccess(reviewId, result);
        mUploaders.remove(reviewId);
        mQueue.workComplete(mTokens.get(reviewId.toString()));
    }

    @Override
    public void onAddedToQueue(String itemId) {
        mTokens.put(itemId, mQueue.addWorker(itemId, this));
        upload(reviewId(itemId));
    }

    private void upload(ReviewId reviewId) {
        if (!mUploading.contains(reviewId)) {
            mUploading.add(reviewId);
            getUploader(reviewId).publishReview();
        }
    }

    @NonNull
    private DatumReviewId reviewId(String itemId) {
        return new DatumReviewId(itemId);
    }

    private void notifyListenersOnFail(ReviewId reviewId, CallbackMessage result) {
        for (BackendConsumerListener listener : mListeners) {
            listener.onUploadFailed(reviewId, result);
        }
    }

    private void notifyListenersOnSuccess(ReviewId reviewId, CallbackMessage result) {
        for (BackendConsumerListener listener : mListeners) {
            listener.onUploadCompleted(reviewId, result);
        }
    }

    @NonNull
    private NetworkPublisher<ReviewUploaderListener> getUploader(ReviewId reviewId) {
        if (!mUploaders.containsKey(reviewId)) {
            NetworkPublisher<ReviewUploaderListener> uploader = newUploader(reviewId);
            uploader.registerListener(this);
            mUploaders.put(reviewId, uploader);
        }

        return mUploaders.get(reviewId);
    }

    private NetworkPublisher<ReviewUploaderListener> newUploader(ReviewId reviewId) {
        return mUploaderFactory.newPublisher(reviewId);
    }
}
