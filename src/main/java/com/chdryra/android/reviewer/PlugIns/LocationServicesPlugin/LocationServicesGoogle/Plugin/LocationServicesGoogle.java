/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Plugin;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.FactoryLocationProviders;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationServicesGoogle implements LocationServicesPlugin {
    private static FactoryLocationProviders sFactory;
    private Context mContext;

    public LocationServicesGoogle(Context context) {
        mContext = context;
    }

    @Override
    public FactoryLocationProviders getLocationProvidersFactory() {
        if(sFactory == null) sFactory = new FactoryLocationProvidersGp(mContext);
        return sFactory;
    }
}
