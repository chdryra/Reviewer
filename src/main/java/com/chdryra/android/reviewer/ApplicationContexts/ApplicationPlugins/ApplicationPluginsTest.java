package com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.LocationServicesGoogle.LocationServicesGoogle;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.DatabasePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.DatabaseAndroidSqlLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.Api.UiPlugin;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.UiAndroidPlugin;

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
        DatabasePlugin dbPlugin = new DatabaseAndroidSqlLite();
        return new PersistenceReviewerDb(PERSISTENCE_NAME, PERSISTENCE_VER, dbPlugin);
    }

    @Override
    public LocationServicesPlugin getLocationServicesPlugin() {
        return new LocationServicesGoogle();
    }

    @Override
    public UiPlugin getUiPlugin() {
        return new UiAndroidPlugin();
    }
}
