package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.LocationServices.Interfaces.ReviewerLocationServices;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextImpl implements ApplicationContext {
    private PresenterContext mPresenterContext;
    private ReviewerLocationServices mLocationServices;

    public ApplicationContextImpl(PresenterContext presenterContext, ReviewerLocationServices locationServices) {
        mPresenterContext = presenterContext;
        mLocationServices = locationServices;
    }

    @Override
    public PresenterContext getContext() {
        return mPresenterContext;
    }

    @Override
    public ReviewerLocationServices getLocationServices() {
        return mLocationServices;
    }
}
