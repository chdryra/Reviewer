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
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.Backend.BackendReviewUploader;
import com.chdryra.android.reviewer.NetworkServices.Backend.ReviewUploaderListener;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.BackendConsumerListener;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactoryBackendUploader;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendConsumer extends QueueConsumer<Review, BackendConsumerListener>
        implements ReviewUploaderListener {
    private FactoryBackendUploader mFactory;

    public BackendConsumer(FactoryBackendUploader factory) {
        mFactory = factory;
    }

    @Override
    public void onUploadedToBackend(ReviewId reviewId, CallbackMessage result) {
        onWorkCompleted(reviewId.toString());
        if(result.isError()) {
            notifyOnFailure(reviewId, result);
        } else {
            notifyOnSuccess(reviewId, result);
        }
    }

    private void notifyOnSuccess(ReviewId reviewId, CallbackMessage result) {
        for(BackendConsumerListener listener : getListeners()) {
            listener.onUploadCompleted(reviewId, result);
        }
    }

    private void notifyOnFailure(ReviewId reviewId, CallbackMessage result) {
        for(BackendConsumerListener listener : getListeners()) {
            listener.onUploadFailed(reviewId, result);
        }
    }

    @Override
    protected void OnFailedToRetrieve(String reviewId, CallbackMessage result) {
        onWorkCompleted(reviewId);
        notifyOnFailure(new DatumReviewId(reviewId), result);
    }

    @Override
    protected ItemWorker<Review> newWorker(String itemId) {
        return new BackendUploadWorker(mFactory.newUploader(new DatumReviewId(itemId)));
    }

    private static class BackendUploadWorker implements ItemWorker<Review> {
        private BackendReviewUploader mUploader;

        public BackendUploadWorker(BackendReviewUploader uploader) {
            mUploader = uploader;
        }

        @Override
        public void doWork(Review review) {
            mUploader.publishReview();
        }
    }
}
