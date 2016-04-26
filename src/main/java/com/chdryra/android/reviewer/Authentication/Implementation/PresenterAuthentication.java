/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.content.Intent;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthenticationHandler;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterAuthentication implements ActivityResultListener, AuthenticatorCallback{
    private AuthenticationHandlerBasic<?> mRequestedProvider;
    private FactoryAuthenticationHandler mHandlerFactory;
    private AuthenticationListener mListener;

    public interface AuthenticationListener {
        void onUserUnknown();

        void onAuthenticated();

        void onAuthenticationFailed(CallbackMessage message);
    }
    
    private PresenterAuthentication(FactoryAuthenticationHandler handlerFactory, AuthenticationListener listener) {
        mHandlerFactory = handlerFactory;
        mListener = listener;
    }

    public void requestAuthentication(EmailLogin login) {
        mRequestedProvider = mHandlerFactory.newAuthenticator(login, this);
        requestAuthentication();
    }

    public void requestAuthentication(FacebookLogin login) {
        mRequestedProvider = mHandlerFactory.newAuthenticator(login, this);
        requestAuthentication();
    }

    public void requestAuthentication(GoogleLogin login) {
        mRequestedProvider = mHandlerFactory.newAuthenticator(login, this);
        requestAuthentication();
    }

    public void requestAuthentication(TwitterLogin login) {
        mRequestedProvider = mHandlerFactory.newAuthenticator(login, this);
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

    @Override
    public void onSuccess(String provider) {
        mListener.onAuthenticated();
    }

    @Override
    public void onFailure(String provider, AuthenticationError error) {
        if(error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
            mListener.onUserUnknown();
        } else {
            mListener.onAuthenticationFailed(CallbackMessage.error(error.toString()));
        }
    }

    public static class Builder {
        public PresenterAuthentication build(AuthenticationListener listener) {
            return new PresenterAuthentication(new FactoryAuthenticationHandler(), listener);
        }
    }
}
