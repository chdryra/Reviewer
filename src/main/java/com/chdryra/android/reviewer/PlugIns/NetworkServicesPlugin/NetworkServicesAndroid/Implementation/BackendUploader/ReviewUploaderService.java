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
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryObserver;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderService extends IntentService implements ReviewsRepositoryObserver {
    public static final String REVIEW_ID = "BackendUploadService.ReviewId";
    public static final String REQUEST_SERVICE = "BackendUploadService.RequestService";
    public static final String UPLOAD_COMPLETED = "BackendUploadService.UploadFinished";
    public static final String DELETE_COMPLETED = "BackendUploadService.DeleteFinished";

    private static final String SERVICE = "BackendUploadService";

    public ReviewUploaderService() {
        super(SERVICE);
    }

    public enum Service {UPLOAD, DELETE}

    @Override
    protected void onHandleIntent(Intent intent) {
        String reviewId = intent.getStringExtra(REVIEW_ID);
        Service service = (Service) intent.getSerializableExtra(REQUEST_SERVICE);

        if (reviewId != null && service != null) {
            requestBackendService(reviewId, service);
        }
    }

    @Override
    public void onReviewAdded(Review review) {
        Intent update = new Intent(UPLOAD_COMPLETED);
        update.putExtra(REVIEW_ID, review.getReviewId().toString());

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        Intent update = new Intent(DELETE_COMPLETED);
        update.putExtra(REVIEW_ID, reviewId.toString());

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    private void requestBackendService(String reviewId, Service service) {
        ApplicationInstance app = ApplicationInstance.getInstance(getApplicationContext());
        ReviewsRepositoryMutable repo = app.getBackendRepository();
        repo.registerObserver(this);

        if(service == Service.UPLOAD) {
            Review review = app.getReview(reviewId);
            repo.addReview(review);
        } else if(service == Service.DELETE) {
            repo.removeReview(new DatumReviewId(reviewId));
        }
    }
}
