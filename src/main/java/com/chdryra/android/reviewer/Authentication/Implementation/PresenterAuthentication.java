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
    private AuthenticationListener mListener;

    private boolean mAuthenticating = false;

    public interface AuthenticationListener {
        void onUserUnknown();

        void onAuthenticated();

        void onAuthenticationFailed(CallbackMessage message);
    }

    private PresenterAuthentication(FactoryCredentialsHandler handlerFactory,
                                    FactoryCredentialsAuthenticator authenticatorFactory,
                                    AuthenticationListener listener) {
        mHandlerFactory = handlerFactory;
        mAuthenticatorFactory = authenticatorFactory;
        mListener = listener;
    }

    public void authenticate(EmailPassword emailPassword) {
        if (!mAuthenticating) authenticateWithCredentials(emailPassword);
    }

    public void authenticate(FacebookLogin login) {
        if (!mAuthenticating) {
            authenticateWithCredentials(mHandlerFactory.newHandler(login,
                    mAuthenticatorFactory.newFacebookAuthenticator(this)));
        }
    }

    public void authenticate(GoogleLogin login) {
        if (!mAuthenticating) {
            authenticateWithCredentials(mHandler = mHandlerFactory.newHandler(login,
                    mAuthenticatorFactory.newGoogleAuthenticator(this)));
        }
    }

    public void authenticate(TwitterLogin login) {
        if (!mAuthenticating) {
            authenticateWithCredentials(mHandler = mHandlerFactory.newHandler(login,
                    mAuthenticatorFactory.newTwitterAuthenticator(this)));
        }
    }

    public void authenticationFinished() {
        mAuthenticating = false;
        mHandler = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHandler != null) mHandler.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(String provider) {
        mListener.onAuthenticated();
        authenticationFinished();
    }

    @Override
    public void onFailure(AuthenticationError error) {
        if (error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
            mListener.onUserUnknown();
        } else {
            mListener.onAuthenticationFailed(CallbackMessage.error(error.toString()));
        }
        authenticationFinished();
    }

    private void authenticateWithCredentials(CredentialsHandler handler) {
        mAuthenticating = true;
        mHandler = handler;
        mHandler.requestCredentials();
    }

    private void authenticateWithCredentials(EmailPassword emailPassword) {
        mAuthenticating = true;
        mAuthenticatorFactory.newEmailAuthenticator(this).authenticate(emailPassword);
    }

    public static class Builder {
        private ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public PresenterAuthentication build(AuthenticationListener listener) {
            return new PresenterAuthentication(new FactoryCredentialsHandler(),
                    new FactoryCredentialsAuthenticator(mApp.getUserAuthenticator()), listener);
        }
    }

}
