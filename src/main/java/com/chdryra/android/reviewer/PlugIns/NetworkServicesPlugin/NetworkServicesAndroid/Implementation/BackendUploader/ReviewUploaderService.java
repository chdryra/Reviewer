/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendUploader;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryError;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryMutableCallback;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderService extends IntentService implements RepositoryCallback, RepositoryMutableCallback {
    public static final String REVIEW_ID = "BackendUploadService.ReviewId";
    public static final String ERROR = "BackendUploadService.Error";
    public static final String REQUEST_SERVICE = "BackendUploadService.RequestService";
    public static final String UPLOAD_COMPLETED = "BackendUploadService.UploadFinished";
    public static final String DELETE_COMPLETED = "BackendUploadService.DeleteFinished";

    private static final String SERVICE = "BackendUploadService";

    private String mReviewId;
    private ApplicationInstance mApp;

    public ReviewUploaderService() {
        super(SERVICE);
    }

    public enum Service {UPLOAD, DELETE}

    @Override
    protected void onHandleIntent(Intent intent) {
        mApp = ApplicationInstance.getInstance(getApplicationContext());
        mReviewId = intent.getStringExtra(REVIEW_ID);
        Service service = (Service) intent.getSerializableExtra(REQUEST_SERVICE);

        if (mReviewId != null && service != null) {
            requestBackendService(mReviewId, service);
        } else {
            broadcastUploadComplete("Review Id is Null");
        }
    }

    @Override
    public void onAdded(Review review, RepositoryError error) {
        broadcastUploadComplete(error.isError() ? error.getMessage() : null);
    }

    @Override
    public void onRemoved(ReviewId reviewId, RepositoryError error) {
        Intent update = new Intent(DELETE_COMPLETED);
        update.putExtra(REVIEW_ID, reviewId.toString());
        if(error.isError()) update.putExtra(ERROR, error.getMessage());

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, RepositoryError error) {
        if(review != null) {
            mApp.getBackendRepository().addReview(review, this);
        } else {
            String message = error.isError() ? error.getMessage() : "Review not found";
            broadcastUploadComplete(message + ": " + mReviewId);
        }
    }

    private void broadcastUploadComplete(@Nullable String errorMessage) {
        Intent update = new Intent(UPLOAD_COMPLETED);
        update.putExtra(REVIEW_ID, mReviewId);
        if(errorMessage != null) update.putExtra(ERROR, errorMessage);

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    @Override
    public void onCollectionFetchedFromRepo(Collection<Review> reviews, RepositoryError error) {

    }

    private void requestBackendService(String reviewId, Service service) {
        if(service == Service.UPLOAD) {
            fetchReviewFromLocalRepoAndUpload(reviewId);
        } else if(service == Service.DELETE) {
            mApp.getBackendRepository().removeReview(new DatumReviewId(reviewId), this);
        }
    }

    private void fetchReviewFromLocalRepoAndUpload(String reviewId) {
        mApp.getLocalRepository().getReview(new DatumReviewId(reviewId), this);
    }
}
