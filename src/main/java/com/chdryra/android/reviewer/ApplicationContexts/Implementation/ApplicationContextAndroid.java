/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.app.Activity;

import com.chdryra.android.mygenerallibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.reviewer.Application.Implementation.AuthenticationSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.LocationServicesSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.RepositorySuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.ReviewBuilderSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.SocialSuiteAndroid;
import com.chdryra.android.reviewer.Application.Implementation.UiSuiteAndroid;
import com.chdryra.android.reviewer.Application.Interfaces.AuthenticationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.LocationServicesSuite;
import com.chdryra.android.reviewer.Application.Interfaces.RepositorySuite;
import com.chdryra.android.reviewer.Application.Interfaces.ReviewBuilderSuite;
import com.chdryra.android.reviewer.Application.Interfaces.SocialSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ApplicationContext;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ApplicationContextAndroid implements ApplicationContext {
    private final AuthenticationSuiteAndroid mAuth;
    private final LocationServicesSuiteAndroid mLocation;
    private final UiSuiteAndroid mUi;
    private final RepositorySuiteAndroid mRepository;
    private final ReviewBuilderSuiteAndroid mBuilder;
    private final SocialSuiteAndroid mSocial;

    private Activity mActivity;

    public ApplicationContextAndroid(AuthenticationSuiteAndroid auth,
                                     LocationServicesSuiteAndroid location, UiSuiteAndroid ui,
                                     RepositorySuiteAndroid repository, ReviewBuilderSuiteAndroid
                                             builder, SocialSuiteAndroid social) {
        mAuth = auth;
        mLocation = location;
        mUi = ui;
        mRepository = repository;
        mBuilder = builder;
        mSocial = social;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
        mAuth.setActivity(mActivity);
        mLocation.setActivity(mActivity);
        mUi.setActivity(mActivity);
    }

    public void setSession(UserSession session) {
        mUi.setSession(session);
        mSocial.setSession(session);
    }

    public void setReturnResult(ActivityResultCode result) {
        if (result != null) mActivity.setResult(result.get(), null);
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
    public SocialSuite getSocialSuite() {
        return mSocial;
    }

    @Override
    public ReviewBuilderSuite getReviewBuilderSuite() {
        return mBuilder;
    }
}
