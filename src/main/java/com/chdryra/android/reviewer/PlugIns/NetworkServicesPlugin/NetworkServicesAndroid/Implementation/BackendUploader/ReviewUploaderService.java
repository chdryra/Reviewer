/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BackendUploader;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepositoryMutable;


import com.chdryra.android.reviewer.Utils.CallbackMessage;
import com.chdryra.android.reviewer.R;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderService extends IntentService implements CallbackRepository,
        CallbackRepositoryMutable {
    public static final String REVIEW_ID = "BackendUploadService.ReviewId";
    public static final String RESULT = "BackendUploadService.Result";
    public static final String REQUEST_SERVICE = "BackendUploadService.RequestService";
    public static final String UPLOAD_COMPLETED = "BackendUploadService.UploadFinished";
    public static final String DELETE_COMPLETED = "BackendUploadService.DeleteFinished";

    private static final int UPLOAD_SUCCESSFUL = R.string.review_uploaded;
    private static final int DELETE_SUCCESSFUL = R.string.review_deleted;
    private static final int UPLOAD_ERROR = R.string.upload_error;
    private static final int DELETE_ERROR = R.string.delete_error;

    private static final String SERVICE = "BackendUploadService";
    private static final String REVIEW_ID_IS_NULL = "Review Id is Null";
    private static final String REVIEW_NOT_FOUND = "Review not found";

    private String mReviewId;
    private ApplicationInstance mApp;

    public enum Service {UPLOAD, DELETE}

    public ReviewUploaderService() {
        super(SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mApp = ApplicationInstance.getInstance(getApplicationContext());
        mReviewId = intent.getStringExtra(REVIEW_ID);
        Service service = (Service) intent.getSerializableExtra(REQUEST_SERVICE);

        if (mReviewId != null && service != null) {
            requestBackendService(mReviewId, service);
        } else {
            broadcastUploadComplete(CallbackMessage.error(REVIEW_ID_IS_NULL));
        }
    }

    @Override
    public void onAddedCallback(Review review, CallbackMessage result) {
        CallbackMessage message;
        if (result.isError()) {
            String messageString = review.getSubject().getSubject() + ": " +
                    getApplicationContext().getString(UPLOAD_ERROR) + " - " + result.getMessage();
            message = CallbackMessage.error(messageString);
        } else {
            String messageString = review.getSubject().getSubject() + ": " +
                    getApplicationContext().getString(UPLOAD_SUCCESSFUL);
            message = CallbackMessage.ok(messageString);
        }

        broadcastUploadComplete(message);
    }

    @Override
    public void onRemovedCallback(ReviewId reviewId, CallbackMessage result) {
        CallbackMessage message;
        if (result.isError()) {
            String messageString = getApplicationContext().getString(DELETE_ERROR) + " - " +
                    result.getMessage();
            message = CallbackMessage.error(messageString);
        } else {
            String messageString = getApplicationContext().getString(DELETE_SUCCESSFUL);
            message = CallbackMessage.ok(messageString);
        }

        broadcastDeleteComplete(message);
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
        if (review != null && !result.isError()) {
            mApp.getBackendRepository().addReview(review, this);
        } else {
            String message = result.isError() ? result.getMessage() : REVIEW_NOT_FOUND;
            broadcastUploadComplete(CallbackMessage.error(message));
        }
    }

    @Override
    public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {

    }

    private void broadcastUploadComplete(CallbackMessage message) {
        broadcastServiceComplete(new Intent(UPLOAD_COMPLETED), message);
    }

    private void broadcastDeleteComplete(CallbackMessage message) {
        broadcastServiceComplete(new Intent(DELETE_COMPLETED), message);
    }

    private void broadcastServiceComplete(Intent update, CallbackMessage message) {
        update.putExtra(REVIEW_ID, mReviewId);
        update.putExtra(RESULT, message);

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    private void requestBackendService(String reviewId, Service service) {
        DatumReviewId id = new DatumReviewId(reviewId);
        if (service == Service.UPLOAD) {
            mApp.getLocalRepository().getReview(id, this);
        } else if (service == Service.DELETE) {
            //TODO make sure removed from local repo too
            mApp.getBackendRepository().removeReview(id, this);
        }
    }
}
