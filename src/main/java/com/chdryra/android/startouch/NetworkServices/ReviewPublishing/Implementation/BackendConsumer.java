/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Implementation;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.AsyncUtils.QueueConsumer;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces
        .FactoryReviewUploader;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewUploader;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.UploadListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendConsumer extends QueueConsumer<Review> {
    private final ArrayList<UploadListener> mListeners;
    private final FactoryReviewUploader mFactory;

    public BackendConsumer(FactoryReviewUploader factory) {
        mFactory = factory;
        mListeners = new ArrayList<>();
    }

    public void registerListener(UploadListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(UploadListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    protected void OnFailedToRetrieve(String reviewId, CallbackMessage result) {
        onWorkCompleted(reviewId);
        notifyOnFailure(new DatumReviewId(reviewId), result);
    }

    @Override
    protected void onWorkerRemoved(ItemWorker<Review> remove) {
        ((BackendUploadWorker) remove).unregister();
    }

    @Override
    protected ItemWorker<Review> newWorker(String itemId) {
        return new BackendUploadWorker(mFactory.newUploader(new DatumReviewId(itemId)));
    }

    private void onUploadComplete(ReviewId reviewId, CallbackMessage result) {
        onWorkCompleted(reviewId.toString());
        if (result.isError()) {
            notifyOnFailure(reviewId, result);
        } else {
            notifyOnSuccess(reviewId, result);
        }
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

    private class BackendUploadWorker implements ItemWorker<Review>, ReviewUploader.Callback {
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
        public void onReviewUploaded(ReviewId reviewId, CallbackMessage result) {
            onUploadComplete(reviewId, result);
        }

        private void unregister() {
            mUploader.unregisterListener(this);
        }
    }
}
