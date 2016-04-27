/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.content.Context;
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
    private FactoryCredentialsHandler mCredentialsHandlerFactory;
    private FactoryCredentialsAuthenticator mAuthenticatorFactory;

    private CredentialsHandler mCredentialsHandler;
    private AuthenticationListener mListener;

    private boolean mAuthenticating = false;

    public interface AuthenticationListener {
        void onUserUnknown();

        void onAuthenticated();

        void onAuthenticationFailed(CallbackMessage message);
    }

    private PresenterAuthentication(FactoryCredentialsHandler credentialsHandlerFactory,
                                    FactoryCredentialsAuthenticator authenticatorFactory,
                                    AuthenticationListener listener) {
        mCredentialsHandlerFactory = credentialsHandlerFactory;
        mAuthenticatorFactory = authenticatorFactory;
        mListener = listener;
    }

    public void authenticate(EmailPassword emailPassword) {
        if(!mAuthenticating) {
            authenticating();
            EmailAuthenticator authenticator = mAuthenticatorFactory.newEmailAuthenticator(this);
            authenticator.authenticate(emailPassword);
        }
    }

    public void authenticate(FacebookLogin login) {
        if(!mAuthenticating) {
            authenticating();
            FacebookAuthenticator authenticator = mAuthenticatorFactory.newFacebookAuthenticator(this);
            mCredentialsHandler = mCredentialsHandlerFactory.newHandler(login, authenticator);
            mCredentialsHandler.requestCredentials();
        }
    }

    public void authenticate(GoogleLogin login) {
        if(!mAuthenticating) {
            authenticating();
            GoogleAuthenticator authenticator = mAuthenticatorFactory.newGoogleAuthenticator(this);
            mCredentialsHandler = mCredentialsHandlerFactory.newHandler(login, authenticator);
            mCredentialsHandler.requestCredentials();
        }
    }

    public void authenticate(TwitterLogin login) {
        if(!mAuthenticating) {
            authenticating();
            TwitterAuthenticator authenticator = mAuthenticatorFactory.newTwitterAuthenticator(this);
            mCredentialsHandler = mCredentialsHandlerFactory.newHandler(login, authenticator);
            mCredentialsHandler.requestCredentials();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCredentialsHandler.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(String provider) {
        mListener.onAuthenticated();
        authenticationFinished();
    }

    @Override
    public void onFailure(String provider, AuthenticationError error) {
        if (error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
            mListener.onUserUnknown();
        } else {
            mListener.onAuthenticationFailed(CallbackMessage.error(error.toString()));
        }
        authenticationFinished();
    }

    private void authenticating() {
        mAuthenticating = true;
    }

    private void authenticationFinished() {
        mAuthenticating = false;
    }

    public static class Builder {
        private Context mContext;
        private ApplicationInstance mApp;

        public Builder(Context context, ApplicationInstance app) {
            mContext = context;
            mApp = app;
        }

        public PresenterAuthentication build(AuthenticationListener listener) {
            return new PresenterAuthentication(new FactoryCredentialsHandler(),
                    new FactoryCredentialsAuthenticator(mContext, mApp.getUserAuthenticator()),
                    listener);
        }
    }

}
