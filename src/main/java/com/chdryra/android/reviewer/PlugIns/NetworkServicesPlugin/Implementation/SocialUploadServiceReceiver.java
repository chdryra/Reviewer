/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Implementation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsUploaderListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialUploadServiceReceiver extends BroadcastReceiver {
    private ArrayList<SocialPlatformsUploaderListener> mListeners;

    public SocialUploadServiceReceiver() {
        mListeners = new ArrayList<>();
    }

    public void registerListener(SocialPlatformsUploaderListener listener) {
        if(!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(SocialPlatformsUploaderListener listener) {
        if(mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(ReviewUploadService.STATUS_UPDATE)) {
            updateListenersOnStatus(intent);
        } else if(action.equals(ReviewUploadService.UPLOAD_COMPLETED)){
            updateListenersOnCompletion(intent);
        }
    }

    private void updateListenersOnCompletion(Intent intent) {
        ArrayList<PublishResults> ok = intent.getParcelableArrayListExtra(ReviewUploadService
                .PUBLISH_RESULTS_OK);
        ArrayList<PublishResults> notOk = intent.getParcelableArrayListExtra(ReviewUploadService
                .PUBLISH_RESULTS_NOT_OK);
        for(SocialPlatformsUploaderListener listener : mListeners) {
            listener.onUploadCompleted(ok, notOk);
        }
    }

    private void updateListenersOnStatus(Intent intent) {
        double percentage = intent.getDoubleExtra(ReviewUploadService.STATUS_PERCENTAGE, 0.);
        PublishResults results = intent.getParcelableExtra(ReviewUploadService.STATUS_RESULTS);
        for(SocialPlatformsUploaderListener listener : mListeners) {
            listener.onUploadStatus(percentage, results);
        }
    }
}
