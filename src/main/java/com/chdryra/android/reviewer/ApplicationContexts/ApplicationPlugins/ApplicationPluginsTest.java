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
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.SocialUploader.NetworkServicesAndroid;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendDatabase.Factories.FactoryBackendFirebase;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Factories.FactoryReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.AndroidSqLiteDb.Plugin.AndroidSqlLiteDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Plugin.FactoryPersistence;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Plugin.PersistencePluginImpl;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.Api.UiPlugin;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Plugin.UiAndroid;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationPluginsTest implements ApplicationPlugins {
    private static final String PERSISTENCE_NAME = "TestReviewer";
    private static final int PERSISTENCE_VER = 1;

    private Context mContext;

    public ApplicationPluginsTest(Context context) {
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
        FactoryPersistence local
                = new FactoryReviewerDb(PERSISTENCE_NAME, PERSISTENCE_VER, new AndroidSqlLiteDb());
        FactoryPersistence backend = new FactoryBackendFirebase();
        return new PersistencePluginImpl(local, backend);
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
