/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.ApplicationPlugins;

import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsPlugin;
import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.Api.DataComparatorsPlugin;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.NetworkServicesPlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.Api.UiPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 13/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface ApplicationPlugins {
    DataComparatorsPlugin getDataComparatorsPlugin();

    DataAggregatorsPlugin getDataAggregatorsPlugin();

    PersistencePlugin getPersistencePlugin();

    LocationServicesPlugin getLocationServicesPlugin();

    NetworkServicesPlugin getNetworkServicesPlugin();

    UiPlugin getUiPlugin();
}
