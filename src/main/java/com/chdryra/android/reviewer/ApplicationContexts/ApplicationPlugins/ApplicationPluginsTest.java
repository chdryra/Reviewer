package com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle
        .LocationServicesGoogle;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api
        .DatabasePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation.DatabaseAndroidSqlLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceDatabase;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationPluginsTest implements ApplicationPlugins {
    private static final String PERSISTENCE_NAME = "TestReviewer";
    private static final int PERSISTENCE_VER = 1;


    @Override
    public PersistencePlugin getPersistencePlugin() {
        return new PersistenceDatabase(PERSISTENCE_NAME, PERSISTENCE_VER, getDatabasePlugin());
    }

    @Override
    public LocationServicesPlugin getLocationServicesPlugin() {
        return new LocationServicesGoogle();
    }

    @NonNull
    private DatabasePlugin getDatabasePlugin() {
        return new DatabaseAndroidSqlLite();
    }
}
