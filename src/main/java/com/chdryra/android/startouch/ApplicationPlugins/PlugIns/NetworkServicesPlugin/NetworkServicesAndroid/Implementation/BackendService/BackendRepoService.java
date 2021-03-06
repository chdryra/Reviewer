/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin
        .NetworkServicesAndroid
        .Implementation.BackendService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.AsyncUtils.WorkerToken;
import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Interfaces.RepositorySuite;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;
import com.chdryra.android.startouch.R;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendRepoService extends IntentService {
    public static final String REVIEW_ID = "BackendUploadService.ReviewId";
    public static final String RESULT = "BackendUploadService.Result";
    public static final String REQUEST_SERVICE = "BackendUploadService.RequestService";

    private static final String UPLOAD_COMPLETED = "BackendUploadService.UploadFinished";
    private static final String DELETE_COMPLETED = "BackendUploadService.DeleteFinished";
    private static final int UPLOAD_SUCCESSFUL = R.string.review_uploaded;
    private static final int DELETE_SUCCESSFUL = R.string.review_deleted;
    private static final int UPLOAD_ERROR = R.string.upload_error;
    private static final int DELETE_ERROR = R.string.delete_error;

    private static final String SERVICE = "BackendUploadService";
    private static final String REVIEW_ID_IS_NULL = "Review Id is Null";
    private static final String SERVICE_NOT_SPECIFIED = "Service not specified";

    private String mReviewId;
    private ReviewPublisher mPublisher;
    private WorkerToken mToken;
    private ReviewsRepoWriteable mRepo;

    public enum Service {
        UPLOAD(UPLOAD_COMPLETED),
        DELETE(DELETE_COMPLETED);

        private final String mCompletionAction;

        Service(String completionAction) {
            mCompletionAction = completionAction;
        }

        public String completed() {
            return mCompletionAction;
        }
    }

    public BackendRepoService() {
        super(SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppInstanceAndroid app = AppInstanceAndroid.getInstance(getApplicationContext());
        RepositorySuite repository = app.getRepository();
        mRepo = repository.getReviews().getRepoForUser(app.getAccounts().getUserSession());
        mPublisher = repository.getReviewPublisher();

        mReviewId = intent.getStringExtra(REVIEW_ID);
        Service service = (Service) intent.getSerializableExtra(REQUEST_SERVICE);

        if (mReviewId != null && service != null) {
            requestBackendService(service);
        } else {
            String error = mReviewId == null ? REVIEW_ID_IS_NULL : SERVICE_NOT_SPECIFIED;
            broadcastUploadComplete(CallbackMessage.error(error));
        }
    }

    private void broadcastUploadComplete(CallbackMessage message) {
        if (mToken != null) mPublisher.workComplete(mToken);
        broadcastServiceComplete(new Intent(Service.UPLOAD.completed()), message);
    }

    private void broadcastDeleteComplete(CallbackMessage message) {
        broadcastServiceComplete(new Intent(Service.DELETE.completed()), message);
    }

    private void broadcastServiceComplete(Intent update, CallbackMessage message) {
        update.putExtra(REVIEW_ID, mReviewId);
        update.putExtra(RESULT, message);

        LocalBroadcastManager.getInstance(this).sendBroadcast(update);
    }

    private void requestBackendService(Service service) {
        final ReviewId id = new DatumReviewId(mReviewId);
        Callbacks callbacks = new Callbacks();
        if (service == Service.UPLOAD) {
            mToken = mPublisher.getFromQueue(id, callbacks, this);
        } else if (service == Service.DELETE) {
            mRepo.removeReview(id, callbacks);
        }
    }

    @NonNull
    private String getErrorString(RepoResult result, int error) {
        return getString(error) + " - " + result.getMessage();
    }

    private class Callbacks implements ReviewPublisher.QueueCallback, ReviewsRepoWriteable
            .Callback {
        @Override
        public void onAddedToRepo(RepoResult result) {
            CallbackMessage message;
            String subject = "";
            if (result.isReview()) {
                subject = result.getReview().getSubject().getSubject();
                message = CallbackMessage.ok(subject + " " + getString(UPLOAD_SUCCESSFUL));
            } else {
                message = CallbackMessage.error(subject + " " + getErrorString(result,
                        UPLOAD_ERROR));
            }

            broadcastUploadComplete(message);
        }

        @Override
        public void onRemovedFromRepo(RepoResult result) {
            CallbackMessage message;
            if (result.isError()) {
                message = CallbackMessage.error(getErrorString(result, DELETE_ERROR));
            } else {
                message = CallbackMessage.ok(getString(DELETE_SUCCESSFUL));
            }
            broadcastDeleteComplete(message);
        }


        @Override
        public void onAddedToQueue(ReviewId id, CallbackMessage message) {

        }

        @Override
        public void onRetrievedFromQueue(Review review, CallbackMessage message) {
            mRepo.addReview(review, this);
        }

        @Override
        public void onFailed(@Nullable Review review, @Nullable ReviewId id, CallbackMessage
                message) {
            broadcastUploadComplete(message);
        }
    }
}
