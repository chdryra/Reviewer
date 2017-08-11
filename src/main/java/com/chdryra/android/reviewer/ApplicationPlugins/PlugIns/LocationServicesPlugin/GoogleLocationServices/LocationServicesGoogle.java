/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.GoogleLocationServices;

import android.content.Context;

import com.chdryra.android.mygenerallibrary.Permissions.PermissionsManager;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationServicesGoogle implements LocationServicesPlugin {
    private static LocationServicesApi sServices;

    private final Context mContext;

    public LocationServicesGoogle(Context context) {
        mContext = context;
    }

    @Override
    public LocationServicesApi getApi(PermissionsManager permissions) {
        if(sServices == null) sServices = new GoogleLocationServicesApi(mContext, permissions);
        return sServices;
    }
}
