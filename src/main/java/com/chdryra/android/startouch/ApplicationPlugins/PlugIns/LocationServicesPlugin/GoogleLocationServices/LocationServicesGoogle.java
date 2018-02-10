/*
 * Copyright (c) Rizwan Choudrey 2017 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.GoogleLocationServices;

import android.content.Context;

import com.chdryra.android.corelibrary.Permissions.PermissionsManager;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServices;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LocationServicesGoogle implements LocationServicesPlugin {
    private static LocationServices sServices;

    private final Context mContext;

    public LocationServicesGoogle(Context context) {
        mContext = context;
    }

    @Override
    public LocationServices getApi(PermissionsManager permissions) {
        if(sServices == null) sServices = new GoogleLocationServices(mContext, permissions);
        return sServices;
    }
}
