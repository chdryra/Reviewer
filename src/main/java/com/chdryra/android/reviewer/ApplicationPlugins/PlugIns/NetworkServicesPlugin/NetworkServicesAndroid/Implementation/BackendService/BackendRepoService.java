/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid
        .Implementation.BackendService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkerToken;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.R;

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

    private String mReviewId;
    private ReviewPublisher mPublisher;
    private WorkerToken mToken;
    private MutableRepository mRepo;

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
        mRepo = repository.getReviewsRepo().getMutableRepository(app.getAccounts().getUserSession());
        mPublisher = repository.getReviewPublisher();

        mReviewId = intent.getStringExtra(REVIEW_ID);
        Service service = (Service) intent.getSerializableExtra(REQUEST_SERVICE);

        if (mReviewId != null && service != null) {
            requestBackendService(service);
        } else {
            broadcastUploadComplete(CallbackMessage.error(REVIEW_ID_IS_NULL));
        }
    }

    private void broadcastUploadComplete(CallbackMessage message) {
        if(mToken != null) mPublisher.workComplete(mToken);
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

    private class Callbacks implements ReviewPublisher.QueueCallback, MutableRepoCallback {
        @Override
        public void onAddedToRepo(RepositoryResult result) {
            CallbackMessage message;
            String subject = "";
            if(result.isReview()) {
                subject = result.getReview().getSubject().getSubject();
                message = CallbackMessage.ok(subject + " " + getString(UPLOAD_SUCCESSFUL));
            } else {
                message = CallbackMessage.error(subject + " " + getErrorString(result, UPLOAD_ERROR));
            }

            broadcastUploadComplete(message);
        }

        @Override
        public void onRemovedFromRepo(RepositoryResult result) {
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


    @NonNull
    private String getErrorString(RepositoryResult result, int error) {
        return getString(error) + " - " + result.getMessage();
    }
}
