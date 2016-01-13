package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.PlugIns.LocationServicesPlugin.Api.LocationServicesPlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextImpl implements ApplicationContext {
    private PresenterContext mPresenterContext;
    private LocationServicesPlugin mLocationServicesPlugin;

    public ApplicationContextImpl(PresenterContext presenterContext, LocationServicesPlugin
            locationServicesPlugin) {
        mPresenterContext = presenterContext;
        mLocationServicesPlugin = locationServicesPlugin;
    }

    @Override
    public PresenterContext getContext() {
        return mPresenterContext;
    }

    @Override
    public LocationServicesPlugin getLocationServicesPlugin() {
        return mLocationServicesPlugin;
    }
}
