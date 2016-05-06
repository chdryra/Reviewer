/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.app.Activity;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryCredentialsAuthenticator;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryCredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterLogin implements ActivityResultListener, AuthenticatorCallback {
    private static final int FEED = RequestCodeGenerator.getCode("FeedScreen");
    private static final int SIGN_UP = RequestCodeGenerator.getCode("SignUpScreen");

    private FactoryCredentialsHandler mHandlerFactory;
    private FactoryCredentialsAuthenticator mAuthenticatorFactory;

    private ApplicationInstance mApp;
    private CredentialsHandler mHandler;
    private LoginListener mListener;

    private boolean mAuthenticating = false;

    public interface LoginListener {
        void onUserUnknown();

        void onAuthenticated();

        void onAuthenticationFailed(CallbackMessage message);
    }

    private PresenterLogin(ApplicationInstance app,
                           FactoryCredentialsHandler handlerFactory,
                           FactoryCredentialsAuthenticator authenticatorFactory,
                           LoginListener listener) {
        mApp = app;
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
            authenticateWithCredentials(mHandlerFactory.newHandler(login,
                    mAuthenticatorFactory.newGoogleAuthenticator(this)));
        }
    }

    public void authenticate(TwitterLogin login) {
        if (!mAuthenticating) {
            authenticateWithCredentials(mHandlerFactory.newHandler(login,
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
    public void onAuthenticated(String provider, AuthenticatedUser user) {
        mListener.onAuthenticated();
        authenticationFinished();
    }

    @Override
    public void onAuthenticationError(AuthenticationError error) {
        if (error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
            mListener.onUserUnknown();
        } else {
            mListener.onAuthenticationFailed(CallbackMessage.error(error.toString()));
        }

        authenticationFinished();
    }

    public void onSignUpNewAuthor(Activity activity) {
        launchLaunchable(activity, mApp.getConfigUi().getSignUpConfig(), SIGN_UP);
    }

    public void onAuthorAuthenticated(Activity activity) {
        launchLaunchable(activity, mApp.getConfigUi().getFeedConfig(), FEED);
    }

    private void launchLaunchable(Activity activity, LaunchableConfig launchable, int code) {
        LaunchableUiLauncher uiLauncher = mApp.getUiLauncher();
        uiLauncher.launch(launchable, activity, code);
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

        public PresenterLogin build(LoginListener listener) {
            return new PresenterLogin(mApp, new FactoryCredentialsHandler(),
                    new FactoryCredentialsAuthenticator(mApp.getUserAuthenticator()), listener);
        }
    }

}
