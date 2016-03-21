/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BackendUploader;

import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.Implementation.BroadcastingServiceReceiver;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewUploaderListener;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUploaderReceiver extends BroadcastingServiceReceiver<ReviewUploaderListener>{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(ReviewUploaderService.UPLOAD_COMPLETED)) {
            updateListenersOnCompletion(intent);
        } else if(action.equals(ReviewUploaderService.DELETE_COMPLETED)){
            updateListenersOnDeletion(intent);
        }
    }

    private void updateListenersOnCompletion(Intent intent) {
        ReviewId id  = intent.getParcelableExtra(ReviewUploaderService.REVIEW_ID);
        for(ReviewUploaderListener listener : this) {
            listener.onUploadCompleted(id);
        }
    }

    private void updateListenersOnDeletion(Intent intent) {
        ReviewId id  = intent.getParcelableExtra(ReviewUploaderService.REVIEW_ID);
        for(ReviewUploaderListener listener : this) {
            listener.onDeleteCompleted(id);
        }
    }
}
