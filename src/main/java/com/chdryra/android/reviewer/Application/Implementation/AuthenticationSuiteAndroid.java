/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Application.Interfaces.AuthenticationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .LoginProviders.GoogleLoginAndroid;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class AuthenticationSuiteAndroid implements AuthenticationSuite {
    private AccountsManager mManager;
    private UserSession mSession;
    private GoogleLoginAndroid mGoogleLogin;

    public AuthenticationSuiteAndroid(AccountsManager manager, UserSession session) {
        mManager = manager;
        mSession = session;
    }

    @Override
    public UserAuthenticator getAuthenticator() {
        return mManager.getAuthenticator();
    }

    @Override
    public UserSession getUserSession() {
        return mSession;
    }

    @Override
    public UserAccounts getUserAccounts() {
        return mManager.getAccounts();
    }

    @Override
    public void logout() {
        mSession.logout(mGoogleLogin);
    }

    public void setActivity(Activity activity) {
        mGoogleLogin = new GoogleLoginAndroid(activity);
    }
}
