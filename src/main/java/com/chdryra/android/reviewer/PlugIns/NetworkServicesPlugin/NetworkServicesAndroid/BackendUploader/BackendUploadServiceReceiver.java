/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.BackendUploader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Social.Interfaces.BackendUploaderListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendUploadServiceReceiver extends BroadcastReceiver {
    private ArrayList<BackendUploaderListener> mListeners;

    public BackendUploadServiceReceiver() {
        mListeners = new ArrayList<>();
    }

    public void registerListener(BackendUploaderListener listener) {
        if(!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(BackendUploaderListener listener) {
        if(mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(BackendUploadService.UPLOAD_COMPLETED)) {
            updateListenersOnCompletion(intent);
        } else if(action.equals(BackendUploadService.DELETE_COMPLETED)){
            updateListenersOnDeletion(intent);
        }
    }

    private void updateListenersOnCompletion(Intent intent) {
        ReviewId id  = intent.getParcelableExtra(BackendUploadService.REVIEW_ID);
        for(BackendUploaderListener listener : mListeners) {
            listener.onUploadCompleted(id);
        }
    }

    private void updateListenersOnDeletion(Intent intent) {
        ReviewId id  = intent.getParcelableExtra(BackendUploadService.REVIEW_ID);
        for(BackendUploaderListener listener : mListeners) {
            listener.onDeleteCompleted(id);
        }
    }
}
