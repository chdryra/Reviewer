/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.Api
        .DataAggregatorsPlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataComparatorsPlugin.Api
        .DataComparatorsPlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api
        .LocationServicesPlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api
        .NetworkServicesPlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Api
        .PersistencePlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.Api.UiPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationPlugins {
    DataComparatorsPlugin getDataComparators();

    DataAggregatorsPlugin getDataAggregators();

    PersistencePlugin getPersistence();

    LocationServicesPlugin getLocationServices();

    NetworkServicesPlugin getNetworkServices();

    UiPlugin getUi();
}
