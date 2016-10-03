/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Application.Implementation.AuthenticationSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.LocationServicesSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.ReviewBuilderSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.ReviewPacker;
import com.chdryra.android.reviewer.Application.Implementation.UiSuiteAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.AuthenticationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.LocationServicesSuite;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuiteAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.PresenterContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.LocationServicesPlugin.Api
        .LocationServicesApi;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextAndroid implements ApplicationContext {
    private final PresenterContext mContext;

    private AuthenticationSuiteAndroid mAuth;
    private LocationServicesSuiteAndroid mLocation;
    private UiSuiteAndroid mUi;
    private RepositorySuite mRepository;
    private ReviewBuilderSuite mBuilder;

    public ApplicationContextAndroid(PresenterContext context,
                                     LocationServicesApi locationServices) {
        mContext = context;
        setAuthenticationSuite(context);
        setLocationServicesSuite(locationServices);
        setUiSuite(context);
        setRepositorySuite(context);
        setReviewBuilderSuite(context);
    }

    @Override
    public PresenterContext getContext() {
        return mContext;
    }

    @Override
    public AuthenticationSuite getAuthenticationSuite() {
        return mAuth;
    }

    @Override
    public LocationServicesSuite getLocationServicesSuite() {
        return mLocation;
    }

    @Override
    public UiSuite getUiSuite() {
        return mUi;
    }

    @Override
    public RepositorySuite getRepositorySuite() {
        return mRepository;
    }

    @Override
    public ReviewBuilderSuite getReviewBuilderSuite() {
        return mBuilder;
    }

    private void setAuthenticationSuite(PresenterContext context) {
        AccountsManager manager = context.getAccountsManager();
        UserSession session = new UserSessionDefault(manager,
                context.getSocialPlatformList(), context.getReviewsFactory());
        mAuth = new AuthenticationSuiteAndroid(manager, session);
    }

    private void setLocationServicesSuite(LocationServicesApi services) {
        mLocation = new LocationServicesSuiteAndroid(services);
    }

    private void setUiSuite(PresenterContext context) {
        mUi = new UiSuiteAndroid(context.getUiConfig(), context.);
    }

    private void setRepositorySuite(PresenterContext context) {
        mRepository = new RepositorySuiteAndroid(context.getMasterRepository(), context.)
    }

    private void setReviewBuilderSuite(PresenterContext context) {
        mBuilder = new ReviewBuilderSuiteAndroid(context.)
    }

    public void setActivity(Activity activity) {
        mAuth.setActivity(activity);
        mLocation.setActivity(activity);
        mUi.setActivity(activity);
    }

    public void setSession(UserSession session) {
        mUi.setSession(session);
    }

    public void setLauncher(ReviewPacker packer) {
        mUi.setLauncher(mContext.newUiLauncher(packer));
    }
}
