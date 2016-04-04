/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Backend;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactoryBackendUploader;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocalToBackendUploader implements ReviewUploaderListener,
        CallbackRepositoryMutable, CallbackRepository {
    private ReviewsRepositoryMutable mQueue;
    private FactoryBackendUploader mUploaderFactory;
    private Map<ReviewId, BackendReviewUploader> mUploaders;
    private ArrayList<ReviewId> mBacklogToDelete;
    private ArrayList<ReviewId> mUploading;
    private ArrayList<LocalToBackendListener> mListeners;
    private QueueCallback mQueueCallback;

    public LocalToBackendUploader(ReviewsRepositoryMutable queue,
                                  FactoryBackendUploader uploaderFactory) {
        mQueue = queue;
        mUploaderFactory = uploaderFactory;
        mUploaders = new HashMap<>();
        mBacklogToDelete = new ArrayList<>();
        mUploading = new ArrayList<>();
        mListeners = new ArrayList<>();
    }

    public void addToQueue(Review review, QueueCallback callback) {
        mQueueCallback = callback;
        mQueue.addReview(review, this);
    }

    public void getFromQueue(ReviewId id, QueueCallback callback) {
        mQueueCallback = callback;
        mQueue.getReview(id, this);
    }

    public void registerListener(LocalToBackendListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(LocalToBackendListener listener) {
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
        mBacklogToDelete.add(reviewId);
        deleteToDelete();
    }

    @Override
    public void onAddedCallback(Review review, CallbackMessage result) {
        if (result.isError()) {
            notifyListenersOnFail(review.getReviewId(), result);
            return;
        }

        mQueueCallback.onAddedToQueue(review.getReviewId(), result);
        upload(review);
    }

    @Override
    public void onRemovedCallback(ReviewId reviewId, CallbackMessage result) {
        if (result.isError()) return;
        mBacklogToDelete.remove(reviewId);
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
        if (review == null || result.isError()) {
            mQueueCallback.onFailed(result);
        } else {
            mQueueCallback.onRetrievedFromQueue(review, result);
        }
    }

    @Override
    public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {
        throw new IllegalStateException("Should never call this!");
    }

    private void notifyListenersOnFail(ReviewId reviewId, CallbackMessage result) {
        for (LocalToBackendListener listener : mListeners) {
            listener.onUploadFailed(reviewId, result);
        }
    }

    private void notifyListenersOnSuccess(ReviewId reviewId, CallbackMessage result) {
        for (LocalToBackendListener listener : mListeners) {
            listener.onUploadCompleted(reviewId, result);
        }
    }

    private void upload(Review review) {
        mUploading.add(review.getReviewId());
        getUploader(review.getReviewId()).uploadReview();
    }

    @NonNull
    private BackendReviewUploader getUploader(ReviewId reviewId) {
        if (!mUploaders.containsKey(reviewId)) {
            BackendReviewUploader uploader = mUploaderFactory.newUploader(reviewId);
            uploader.registerListener(this);
            mUploaders.put(reviewId, uploader);
        }

        return mUploaders.get(reviewId);
    }

    private void deleteToDelete() {
        for (ReviewId toDelete : mBacklogToDelete) {
            mQueue.removeReview(toDelete, this);
        }
    }
}
