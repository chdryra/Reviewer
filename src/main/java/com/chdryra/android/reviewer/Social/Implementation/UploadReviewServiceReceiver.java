/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UploadReviewServiceReceiver extends BroadcastReceiver {
    private UploadReviewServiceListener mListener;

    public UploadReviewServiceReceiver(UploadReviewServiceListener listener) {
        mListener = listener;
    }

    public interface UploadReviewServiceListener {
        void onStatusUpdate(double percentage, PublishResults justUploaded);

        void onUploadCompleted(Collection<PublishResults> publishedOk,
                               Collection<PublishResults> publishedNotOk);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(ReviewUploadService.STATUS_UPDATE)) {
            updateListenerOnStatus(intent);
        } else if(action.equals(ReviewUploadService.UPLOAD_COMPLETED)){
            updateListenerOnCompletion(intent);
        }
    }

    private void updateListenerOnCompletion(Intent intent) {
        ArrayList<PublishResults> ok = intent.getParcelableArrayListExtra(ReviewUploadService
                .PUBLISH_RESULTS_OK);
        ArrayList<PublishResults> notOk = intent.getParcelableArrayListExtra(ReviewUploadService
                .PUBLISH_RESULTS_NOT_OK);
        mListener.onUploadCompleted(ok, notOk);
    }

    private void updateListenerOnStatus(Intent intent) {
        double percentage = intent.getDoubleExtra(ReviewUploadService.STATUS_PERCENTAGE, 0.);
        PublishResults results = intent.getParcelableExtra(ReviewUploadService.STATUS_RESULTS);
        mListener.onStatusUpdate(percentage, results);
    }
}
