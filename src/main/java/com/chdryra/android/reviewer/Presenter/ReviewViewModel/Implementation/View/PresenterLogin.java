/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Strings;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryCredentialsAuthenticator;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryCredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.EmailValidation;
import com.chdryra.android.reviewer.Authentication.Implementation.PasswordValidation;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailAddressException;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.UiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.SignUpArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterLogin implements ActivityResultListener, AuthenticatorCallback, UserSession.SessionObserver {
    private static final int FEED = RequestCodeGenerator.getCode("FeedScreen");
    private static final int SIGN_UP = RequestCodeGenerator.getCode("SignUpScreen");

    private FactoryCredentialsHandler mHandlerFactory;
    private FactoryCredentialsAuthenticator mAuthenticatorFactory;

    private ApplicationInstance mApp;
    private LoginListener mListener;
    private CredentialsHandler mHandler;

    private boolean mAuthenticating = false;

    public interface LoginListener {
        void onSignUpRequested(@Nullable AuthenticatedUser user, String message);

        void onAuthenticated();

        void onAuthenticationFailed(AuthenticationError error);

        void onNoCurrentUser();

        void onLoggedIn();
    }

    private PresenterLogin(ApplicationInstance app,
                           FactoryCredentialsHandler handlerFactory,
                           FactoryCredentialsAuthenticator authenticatorFactory,
                           LoginListener listener) {
        mApp = app;
        mHandlerFactory = handlerFactory;
        mAuthenticatorFactory = authenticatorFactory;
        mListener = listener;
        startSessionObservation();
    }

    public void startSessionObservation() {
        mApp.getUserSession().setSessionObserver(this);
    }

    @NonNull
    public String getSignUpMessage() {
        return Strings.Alerts.NEW_USER;
    }

    public boolean hasAuthenticatedUser() {
        return mApp.getUserSession().isAuthenticated();
    }

    public void logIn(EmailPassword emailPassword) {
        if (!mAuthenticating) authenticateWithCredentials(emailPassword);
    }

    public void logIn(FacebookLogin login) {
        if (!mAuthenticating) {
            authenticateWithCredentials(mHandlerFactory.newHandler(login,
                    mAuthenticatorFactory.newFacebookAuthenticator(this)));
        }
    }

    public void logIn(GoogleLogin login) {
        if (!mAuthenticating) {
            authenticateWithCredentials(mHandlerFactory.newHandler(login,
                    mAuthenticatorFactory.newGoogleAuthenticator(this)));
        }
    }

    public void logIn(TwitterLogin login) {
        if (!mAuthenticating) {
            authenticateWithCredentials(mHandlerFactory.newHandler(login,
                    mAuthenticatorFactory.newTwitterAuthenticator(this)));
        }
    }

    public void authenticationFinished() {
        mAuthenticating = false;
        mHandler = null;
    }

    public void signUpNewAuthor(String email) {
        try {
            launchSignUp(new SignUpArgs(new EmailAddress(email)));
        } catch (EmailAddressException e) {
            launchSignUp(new SignUpArgs());
        }
    }

    public void signUpNewAuthor(AuthenticatedUser user) {
        launchSignUp(new SignUpArgs(user));
    }

    public void onLoginComplete() {
        UserSession userSession = mApp.getUserSession();
        userSession.unsetSessionObserver();
        launchLaunchable(mApp.getConfigUi().getUsersFeed(), FEED, new Bundle());
        mListener.onLoggedIn();
    }

    public EmailValidation validateEmail(String email) {
        return new EmailValidation(email);
    }

    public PasswordValidation validatePassword(String password) {
        return new PasswordValidation(password);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SIGN_UP) {
            mApp.getCurrentScreen().showToast(Strings.Toasts.COMPLETING_SIGNUP);
            if(data != null) {
                EmailPassword emailPassword = data.getParcelableExtra(PresenterSignUp.EMAIL_PASSWORD);
                if (emailPassword != null) authenticateWithCredentials(emailPassword);
            } else {
                mApp.getUserSession().refreshSession();
            }
        } else {
            if (mHandler != null) mHandler.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onAuthenticated(AuthenticatedUser user) {
        //wait for onLogIn to be called
    }

    @Override
    public void onLogIn(@Nullable UserAccount account,
                        @Nullable AuthenticationError error) {
        if (error == null && account != null) {
            if (mListener != null) mListener.onAuthenticated();
        } else {
            if(error == null) {
                error = new AuthenticationError(ApplicationInstance.APP_NAME, AuthenticationError
                        .Reason.UNKNOWN_ERROR);
            }
            resolveError(account != null ? account.getAccountHolder() : null, error);
        }
        authenticationFinished();
    }

    @Override
    public void onAuthenticationError(AuthenticationError error) {
        resolveError(null, error);
        authenticationFinished();
    }

    private void launchSignUp(SignUpArgs signUpArgs) {
        Bundle args = new Bundle();
        ParcelablePacker<SignUpArgs> packer = new ParcelablePacker<>();
        packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, signUpArgs, args);
        launchLaunchable(mApp.getConfigUi().getSignUp(), SIGN_UP, args);
    }

    private void resolveError(@Nullable AuthenticatedUser user, AuthenticationError error) {
        if (mListener != null) {
            if (error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
                mListener.onSignUpRequested(user, getSignUpMessage());
            } else if (error.is(AuthenticationError.Reason.NO_AUTHENTICATED_USER)) {
                mListener.onNoCurrentUser();
            } else {
                mApp.getCurrentScreen().showToast(Strings.Toasts.LOGIN_UNSUCCESSFUL + ": " + error);
                mListener.onAuthenticationFailed(error);
            }
        }
    }

    private void launchLaunchable(LaunchableConfig launchable, int code, Bundle args) {
        UiLauncher uiLauncher = mApp.getUiLauncher();
        uiLauncher.launch(launchable, code, args);
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
                    new FactoryCredentialsAuthenticator(mApp.getUsersManager().getAuthenticator()), listener);
        }
    }

}
