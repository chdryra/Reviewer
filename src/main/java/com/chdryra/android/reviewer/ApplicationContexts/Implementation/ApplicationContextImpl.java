package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.LocationServices.Interfaces.LocationServicesSuite;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextImpl implements ApplicationContext {
    private PresenterContext mPresenterContext;
    private LocationServicesSuite mLocationServicesSuite;

    public ApplicationContextImpl(PresenterContext presenterContext, LocationServicesSuite
            locationServicesSuite) {
        mPresenterContext = presenterContext;
        mLocationServicesSuite = locationServicesSuite;
    }

    @Override
    public PresenterContext getContext() {
        return mPresenterContext;
    }

    @Override
    public LocationServicesSuite getLocationServicesSuite() {
        return mLocationServicesSuite;
    }
}
