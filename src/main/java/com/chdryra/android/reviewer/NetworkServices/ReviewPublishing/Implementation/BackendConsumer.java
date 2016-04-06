/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
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
public class BackendConsumer extends ReviewConsumer<BackendConsumerListener> implements ReviewUploaderListener {
    private FactoryBackendUploader mUploaderFactory;

    public BackendConsumer(FactoryBackendUploader uploaderFactory) {
        mUploaderFactory = uploaderFactory;
    }

    @Override
    public void onUploadedToBackend(ReviewId reviewId, CallbackMessage result) {
        onWorkCompleted(reviewId);
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
    protected void OnFailedToRetrieve(ReviewId reviewId, CallbackMessage result) {
        onWorkCompleted(reviewId);
        notifyOnFailure(reviewId, result);
    }

    @Override
    protected ReviewWorker newWorker(ReviewId reviewId) {
        return new BackendUploadWorker(mUploaderFactory.newPublisher(reviewId));
    }

    private class BackendUploadWorker implements ReviewWorker {
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
