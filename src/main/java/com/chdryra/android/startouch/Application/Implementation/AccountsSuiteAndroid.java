/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Application.Implementation;

import android.app.Activity;

import com.chdryra.android.startouch.Application.Interfaces.AccountsSuite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .LoginProviders.LoginGoogleAndroid;
import com.chdryra.android.startouch.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class AccountsSuiteAndroid implements AccountsSuite {
    private AccountsManager mManager;
    private UserSession mSession;
    private LoginGoogleAndroid mGoogleLogin;

    public AccountsSuiteAndroid(AccountsManager manager, UserSession session) {
        mManager = manager;
        mSession = session;
    }

    public void setActivity(Activity activity) {
        mGoogleLogin = new LoginGoogleAndroid(activity);
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
}
