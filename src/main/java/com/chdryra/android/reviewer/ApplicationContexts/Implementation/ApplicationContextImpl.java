package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationServicesProvider;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextImpl implements ApplicationContext {
    private PresenterContext mPresenterContext;
    private LocationServicesProvider mLocationServicesProvider;

    public ApplicationContextImpl(PresenterContext presenterContext, LocationServicesProvider
            locationServicesProvider) {
        mPresenterContext = presenterContext;
        mLocationServicesProvider = locationServicesProvider;
    }

    @Override
    public PresenterContext getContext() {
        return mPresenterContext;
    }

    @Override
    public LocationServicesProvider getLocationServicesProvider() {
        return mLocationServicesProvider;
    }
}
