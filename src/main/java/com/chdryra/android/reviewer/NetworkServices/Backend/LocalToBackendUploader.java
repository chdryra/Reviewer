/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Backend;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
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
public class LocalToBackendUploader implements ReviewUploaderListener {
    private ReviewQueue mQueue;
    private FactoryBackendUploader mUploaderFactory;
    private Map<ReviewId, BackendReviewUploader> mUploaders;
    private ArrayList<ReviewId> mUploading;
    private ArrayList<LocalToBackendListener> mListeners;

    public LocalToBackendUploader(ReviewQueue queue,
                                  FactoryBackendUploader uploaderFactory) {
        mQueue = queue;
        mUploaderFactory = uploaderFactory;
        mUploaders = new HashMap<>();
        mUploading = new ArrayList<>();
        mListeners = new ArrayList<>();
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
}
