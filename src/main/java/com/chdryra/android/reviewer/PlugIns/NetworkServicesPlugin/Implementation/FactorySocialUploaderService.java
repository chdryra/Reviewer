/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactorySocialUploader;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsUploader;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactorySocialUploaderService implements FactorySocialUploader {
    private Context mContext;

    public FactorySocialUploaderService(Context context) {
        mContext = context;
    }

    @Override
    public SocialPlatformsUploader newReviewUploader() {
        return new SocialPlatformsUploaderAndroid(mContext, new SocialUploadServiceReceiver());
    }
}
