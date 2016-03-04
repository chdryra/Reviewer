/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Implementation;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.Social.Implementation.ReviewUploadService;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewUploader;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewUploaderListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderAndroid implements ReviewUploader {
    private Context mContext;
    private ReviewUploadServiceReceiver mReceiver;

    public ReviewUploaderAndroid(Context context, ReviewUploadServiceReceiver receiver) {
        mContext = context;
        mReceiver = receiver;
        IntentFilter statusUpdateFilter = new IntentFilter(ReviewUploadService.STATUS_UPDATE);
        IntentFilter uploadCompletedFilter = new IntentFilter(ReviewUploadService.UPLOAD_COMPLETED);


        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, statusUpdateFilter);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, uploadCompletedFilter);

    }

    @Override
    public void registerListener(ReviewUploaderListener listener) {
        mReceiver.registerListener(listener);
    }

    @Override
    public void unregisterListener(ReviewUploaderListener listener) {
        mReceiver.unregisterListener(listener);
    }

    @Override
    public void upload(String reviewId, ArrayList<String> platformNames) {
        Intent shareService = new Intent(mContext, ReviewUploadService.class);
        shareService.putExtra(PublishingAction.PUBLISHED, reviewId);
        shareService.putStringArrayListExtra(PublishingAction.PLATFORMS, platformNames);
        mContext.startService(shareService);
    }
}
