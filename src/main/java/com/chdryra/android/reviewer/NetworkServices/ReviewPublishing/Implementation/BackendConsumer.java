/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.AsyncUtils.QueueConsumer;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewUploader;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.UploadListener;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api.FactoryBackendUploader;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendConsumer extends QueueConsumer<Review> {
    private final ArrayList<UploadListener> mListeners;
    private final FactoryBackendUploader mFactory;

    public BackendConsumer(FactoryBackendUploader factory) {
        mFactory = factory;
        mListeners = new ArrayList<>();
    }

    public void registerListener(UploadListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(UploadListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    private void onUploadComplete(ReviewId reviewId, CallbackMessage result) {
        onWorkCompleted(reviewId.toString());
        if (result.isError()) {
            notifyOnFailure(reviewId, result);
        } else {
            notifyOnSuccess(reviewId, result);
        }
    }

    @Override
    protected void OnFailedToRetrieve(String reviewId, CallbackMessage result) {
        onWorkCompleted(reviewId);
        notifyOnFailure(new DatumReviewId(reviewId), result);
    }

    @Override
    protected void onWorkerRemoved(ItemWorker<Review> remove) {
        ((BackendUploadWorker)remove).unregister();
    }

    @Override
    protected ItemWorker<Review> newWorker(String itemId) {
        return new BackendUploadWorker(mFactory.newUploader(new DatumReviewId(itemId)));
    }

    private void notifyOnSuccess(ReviewId reviewId, CallbackMessage result) {
        for (UploadListener listener : mListeners) {
            listener.onUploadCompleted(reviewId, result);
        }
    }

    private void notifyOnFailure(ReviewId reviewId, CallbackMessage result) {
        for (UploadListener listener : mListeners) {
            listener.onUploadFailed(reviewId, result);
        }
    }

    private class BackendUploadWorker implements ItemWorker<Review>, ReviewUploader.Listener{
        private final ReviewUploader mUploader;

        private BackendUploadWorker(ReviewUploader uploader) {
            mUploader = uploader;
        }

        @Override
        public void doWork(Review review) {
            mUploader.registerListener(this);
            mUploader.uploadReview();
        }

        @Override
        public void onUploadedToBackend(ReviewId reviewId, CallbackMessage result) {
            onUploadComplete(reviewId, result);
        }

        private void unregister() {
            mUploader.unregisterListener(this);
        }
    }
}
