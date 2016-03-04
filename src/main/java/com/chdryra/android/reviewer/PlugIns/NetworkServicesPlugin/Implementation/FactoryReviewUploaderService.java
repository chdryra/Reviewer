/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactoryReviewUploader;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewUploader;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewUploaderService implements FactoryReviewUploader{
    private Context mContext;

    public FactoryReviewUploaderService(Context context) {
        mContext = context;
    }

    @Override
    public ReviewUploader newReviewUploader() {
        return new ReviewUploaderAndroid(mContext, new ReviewUploadServiceReceiver());
    }
}
