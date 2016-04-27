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
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryCredentialsAuthenticator;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryCredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterAuthentication implements ActivityResultListener, AuthenticatorCallback {
    private FactoryCredentialsHandler mHandlerFactory;
    private FactoryCredentialsAuthenticator mAuthenticatorFactory;

    private CredentialsHandler mHandler;
    private Authenticator mAuthenticator;
    private AuthenticationListener mListener;

    public interface AuthenticationListener {
        void onUserUnknown();

        void onAuthenticated();

        void onAuthenticationFailed(CallbackMessage message);
    }

    private PresenterAuthentication(FactoryCredentialsHandler handlerFactory,
                                    Authenticator authenticator, AuthenticationListener listener) {
        mHandlerFactory = handlerFactory;
        mAuthenticator = authenticator;
        mListener = listener;
    }

    public void requestAuthentication(EmailPassword emailPassword) {
        mAuthenticator.authenticateEmailPasswordCredentials(emailPassword.getEmail().toString(),
                emailPassword.getPassword().toString(), this);
    }

    public void requestCredentials(FacebookLogin login) {
        mHandler = mHandlerFactory.newHandler(login, new FacebookAuthenticator());
        requestCredentials();
    }

    public void requestCredentials(GoogleLogin login) {
        mHandler = mHandlerFactory.newHandler(login, new GoogleAuthenticator());
        requestCredentials();
    }

    public void requestCredentials(TwitterLogin login) {
        mHandler = mHandlerFactory.newHandler(login, new TwitterAuthenticator());
        requestCredentials();
    }

    public void onCredentialsFailure(String provider, AuthenticationError error) {
        mListener.onAuthenticationFailed(CallbackMessage.error(provider + " credentials: " +
                error.toString()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mHandler.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSuccess(String provider) {
        mListener.onAuthenticated();
    }

    @Override
    public void onFailure(String provider, AuthenticationError error) {
        if (error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
            mListener.onUserUnknown();
        } else {
            mListener.onAuthenticationFailed(CallbackMessage.error(error.toString()));
        }
    }

    private void requestCredentials() {
        mHandler.requestCredentials();
    }

    public static class Builder {
        private ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public PresenterAuthentication build(AuthenticationListener listener) {
            Authenticator authenticator = mApp.getUserAuthenticator();
            return new PresenterAuthentication(new FactoryCredentialsHandler(), authenticator,
                    listener);
        }
    }

}
