/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.content.Intent;

import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthenticationHandler;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Authenticator implements ActivityResultListener{
    private AuthenticationHandlerBasic<?> mRequestedProvider;
    private FactoryAuthenticationHandler mHandlerFactory;

    public Authenticator(FactoryAuthenticationHandler handlerFactory) {
        mHandlerFactory = handlerFactory;
    }

    public void requestAuthentication(EmailLogin login, AuthenticatorCallback listener) {
        mRequestedProvider = mHandlerFactory.newAuthenticator(login, listener);
        requestAuthentication();
    }

    public void requestAuthentication(FacebookLogin login, AuthenticatorCallback listener) {
        mRequestedProvider = mHandlerFactory.newAuthenticator(login, listener);
        requestAuthentication();
    }

    public void requestAuthentication(GoogleLogin login, AuthenticatorCallback listener) {
        mRequestedProvider = mHandlerFactory.newAuthenticator(login, listener);
        requestAuthentication();
    }

    public void requestAuthentication(TwitterLogin login, AuthenticatorCallback listener) {
        mRequestedProvider = mHandlerFactory.newAuthenticator(login, listener);
        requestAuthentication();
    }

    private void requestAuthentication() {
        mRequestedProvider.requestAuthentication();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            ActivityResultListener provider = (ActivityResultListener)mRequestedProvider.getProvider();
            provider.onActivityResult(requestCode, resultCode, data);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

}
