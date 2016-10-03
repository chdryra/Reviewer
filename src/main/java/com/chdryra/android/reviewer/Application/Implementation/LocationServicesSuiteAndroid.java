/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.app.Activity;

import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClient;
import com.chdryra.android.mygenerallibrary.LocationUtils.LocationClientConnector;
import com.chdryra.android.reviewer.Application.Interfaces.LocationServicesSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api
        .LocationServicesApi;

/**
 * Created by: Rizwan Choudrey
 * On: 03/10/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class LocationServicesSuiteAndroid implements LocationServicesSuite {
    private LocationServicesApi mApi;
    private Activity mActivity;

    public LocationServicesSuiteAndroid(LocationServicesApi api) {
        mApi = api;
    }

    @Override
    public LocationServicesApi getApi() {
        return mApi;
    }

    @Override
    public LocationClient newLocationClient() {
        return new LocationClientConnector(mActivity);
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }
}
