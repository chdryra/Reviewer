/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
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
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterAuthentication implements ActivityResultListener, AuthenticatorCallback, GoogleAuthenticator.UserRecoverableExceptionHandler {
    private static final int REQUEST_AUTHORIZATION = RequestCodeGenerator.getCode("RequestAuthorisation");

    private FactoryCredentialsHandler mCredentialsHandlerFactory;
    private FactoryCredentialsAuthenticator mAuthenticatorFactory;

    private CredentialsHandler mCredentialsHandler;
    private AuthenticationListener mListener;

    private boolean mAuthenticating = false;

    private GoogleAuthenticator mGoogleAuthenticator;
    private String mGoogleProvder;
    private GoogleSignInAccount mGoogleAccount;

    public interface AuthenticationListener {
        void onUserUnknown();

        void onAuthenticated();

        void onAuthenticationFailed(CallbackMessage message);

        void onGoogleAuthorisationRequired(UserRecoverableAuthException e, int requestCode);
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
            mGoogleAuthenticator = mAuthenticatorFactory.newGoogleAuthenticator(this, this);
            mCredentialsHandler = mCredentialsHandlerFactory.newHandler(login, mGoogleAuthenticator);
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
        if(requestCode == REQUEST_AUTHORIZATION) {
            if(requestCode == Activity.RESULT_OK) {
                mGoogleAuthenticator.onCredentialsObtained(mGoogleProvder, mGoogleAccount);
            } else {
                onFailure(mGoogleProvder, new AuthenticationError(mGoogleProvder, AuthenticationError.Reason.AUTHORISATION_REFUSED));
            }
        } else {
            mCredentialsHandler.onActivityResult(requestCode, resultCode, data);
        }
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

    public void cancelAuthenticating() {
        mAuthenticating = false;
    }

    private void authenticationFinished() {
        mAuthenticating = false;
    }

    @Override
    public void onAuthorisationRequired(String provider, GoogleSignInAccount credentials,
                                        UserRecoverableAuthException e) {
        mListener.onGoogleAuthorisationRequired(e, REQUEST_AUTHORIZATION);
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
