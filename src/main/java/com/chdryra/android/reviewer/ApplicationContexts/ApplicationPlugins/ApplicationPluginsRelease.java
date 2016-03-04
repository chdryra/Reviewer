/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins;

import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsPlugin;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin.DataAggregatorsDefault;
import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.Api.DataComparatorsPlugin;
import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Plugin.DataComparatorsDefault;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.Plugin.LocationServicesGoogle;

import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Implementation
        .NetworkServicesAndroid;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .Api.DatabasePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Plugin.DatabaseAndroidSqlLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Plugin.PersistenceReviewerDb;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.Api.UiPlugin;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationPluginsRelease implements ApplicationPlugins {
    private static final String PERSISTENCE_NAME = "Reviewer";
    private static final int PERSISTENCE_VER = 1;
    private Context mContext;

    public ApplicationPluginsRelease(Context context) {
        mContext = context;
    }

    @Override
    public DataComparatorsPlugin getDataComparatorsPlugin() {
        return new DataComparatorsDefault();
    }

    @Override
    public DataAggregatorsPlugin getDataAggregatorsPlugin() {
        return new DataAggregatorsDefault();
    }

    @Override
    public PersistencePlugin getPersistencePlugin() {
        DatabasePlugin dbPlugin = new DatabaseAndroidSqlLite();
        return new PersistenceReviewerDb(PERSISTENCE_NAME, PERSISTENCE_VER, dbPlugin);
    }

    @Override
    public LocationServicesPlugin getLocationServicesPlugin() {
        return new LocationServicesGoogle(mContext);
    }

    @Override
    public NetworkServicesPlugin getNetworkServicesPlugin() {
        return new NetworkServicesAndroid(mContext);
    }

    @Override
    public UiPlugin getUiPlugin() {
        return new UiAndroid();
    }
}
