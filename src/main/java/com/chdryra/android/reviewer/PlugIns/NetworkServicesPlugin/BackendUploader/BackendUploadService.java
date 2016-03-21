/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.BackendUploader;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Implementation.PublishingAction;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendUploadService extends IntentService implements ReviewsRepositoryObserver {
    public static final String REVIEW_ID = "BackendUploadService.ReviewId";
    public static final String UPLOAD_COMPLETED = "BackendUploadService.UploadFinished";
    public static final String DELETE_COMPLETED = "BackendUploadService.DeleteFinished";

    private static final String SERVICE = "ReviewSharerService";

    public BackendUploadService() {
        super(SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String reviewId = intent.getStringExtra(PublishingAction.PUBLISHED);

        if (reviewId != null) uploadToBackend(reviewId);
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

    private void uploadToBackend(String reviewId) {
        ApplicationInstance app = ApplicationInstance.getInstance(getApplicationContext());
        Review review = app.getReviewFromLocalRepo(reviewId);
        TagsManager tagsManager = app.getTagsManager();


    }
}
