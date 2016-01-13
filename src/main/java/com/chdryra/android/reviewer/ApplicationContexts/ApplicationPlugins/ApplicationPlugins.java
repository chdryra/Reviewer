package com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins;

import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationPlugins {
    PersistencePlugin getPersistencePlugin();

    LocationServicesPlugin getLocationServicesPlugin();
}
