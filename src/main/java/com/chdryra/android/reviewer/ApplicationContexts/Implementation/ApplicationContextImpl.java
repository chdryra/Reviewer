/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.Application.Implementation.AuthenticationSuiteImpl;
import com.chdryra.android.reviewer.Application.Interfaces.AuthenticationSuite;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api.LocationServicesApi;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextImpl implements ApplicationContext {
    private final PresenterContext mContext;
    private final LocationServicesApi mLocationServices;

    private AuthenticationSuite mAuth;

    public ApplicationContextImpl(PresenterContext context, LocationServicesApi locationServices) {
        mContext = context;
        mLocationServices = locationServices;
        setAuthenticationSuite();
    }

    @Override
    public PresenterContext getContext() {
        return mContext;
    }

    @Override
    public LocationServicesApi getLocationServices() {
        return mLocationServices;
    }

    @Override
    public AuthenticationSuite getAuthenticationSuite() {
        return mAuth;
    }

    private void setAuthenticationSuite() {
        AccountsManager manager = mContext.getAccountsManager();
        UserSession session = new UserSessionDefault(manager,
                mContext.getSocialPlatformList(), mContext.getReviewsFactory());
        mAuth = new AuthenticationSuiteImpl(manager, session);
    }
}
