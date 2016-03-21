/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.BackendUploader;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Social.Interfaces.BackendUploader;
import com.chdryra.android.reviewer.Social.Interfaces.BackendUploaderListener;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewUploaderAndroid implements BackendUploader{
    private Context mContext;
    private BackendUploadServiceReceiver mReceiver;

    public BackendReviewUploaderAndroid(Context context, BackendUploadServiceReceiver receiver) {
        mContext = context;
        mReceiver = receiver;

        IntentFilter uploadCompletedFilter = new IntentFilter(BackendUploadService.UPLOAD_COMPLETED);
        IntentFilter deleteCompletedFilter = new IntentFilter(BackendUploadService.DELETE_COMPLETED);

        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, uploadCompletedFilter);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, deleteCompletedFilter);
    }

    @Override
    public void uploadReview(ReviewId reviewId) {
        requestService(reviewId, BackendUploadService.Service.UPLOAD);
    }

    @Override
    public void deleteReview(ReviewId reviewId) {
        requestService(reviewId, BackendUploadService.Service.DELETE);
    }

    private void requestService(ReviewId reviewId, BackendUploadService.Service serviceType) {
        Intent service = new Intent(mContext, BackendUploadService.class);
        service.putExtra(BackendUploadService.REVIEW_ID, reviewId.toString());
        service.putExtra(BackendUploadService.REQUEST_SERVICE, serviceType);
        mContext.startService(service);
    }

    @Override
    public void registerListener(BackendUploaderListener listener) {
        mReceiver.registerListener(listener);
    }

    @Override
    public void unregisterListener(BackendUploaderListener listener) {
        mReceiver.unregisterListener(listener);
    }
}
