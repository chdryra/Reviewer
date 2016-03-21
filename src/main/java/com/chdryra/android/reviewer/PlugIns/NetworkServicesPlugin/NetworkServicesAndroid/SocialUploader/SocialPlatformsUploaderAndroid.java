/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.NetworkServicesAndroid.SocialUploader;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Social.Implementation.PublishingAction;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsUploader;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsUploaderListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformsUploaderAndroid implements SocialPlatformsUploader {
    private Context mContext;
    private SocialUploadServiceReceiver mReceiver;

    public SocialPlatformsUploaderAndroid(Context context, SocialUploadServiceReceiver receiver) {
        mContext = context;
        mReceiver = receiver;

        IntentFilter statusUpdateFilter = new IntentFilter(SocialUploadService.STATUS_UPDATE);
        IntentFilter uploadCompletedFilter = new IntentFilter(SocialUploadService.UPLOAD_COMPLETED);

        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, statusUpdateFilter);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, uploadCompletedFilter);    }

    @Override
    public void registerListener(SocialPlatformsUploaderListener listener) {
        mReceiver.registerListener(listener);
    }


    @Override
    public void unregisterListener(SocialPlatformsUploaderListener listener) {
        mReceiver.unregisterListener(listener);
    }

    @Override
    public void uploadToSocialPlatforms(ReviewId reviewId, ArrayList<String> platformNames) {
        Intent shareService = new Intent(mContext, SocialUploadService.class);
        shareService.putExtra(PublishingAction.PUBLISHED, reviewId.toString());
        shareService.putStringArrayListExtra(PublishingAction.PLATFORMS, platformNames);
        mContext.startService(shareService);
    }
}
