/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.SocialUploader;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactorySocialUploader;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 04/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class NetworkServicesAndroid implements NetworkServicesPlugin {
    private Context mContext;
    private final FactorySocialUploaderService mFactory;

    public NetworkServicesAndroid(Context context) {
        mFactory = new FactorySocialUploaderService(context);
    }

    @Override
    public FactorySocialUploader getSocialUploaderFactory(Context context) {
        return mFactory;
    }
}
