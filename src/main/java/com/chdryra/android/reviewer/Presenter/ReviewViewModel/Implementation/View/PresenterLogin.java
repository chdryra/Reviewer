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

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.AuthenticationSuite;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
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
import com.chdryra.android.reviewer.Utils.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailAddressException;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.ProfileArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.reviewer.View.Configs.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterLogin implements ActivityResultListener, AuthenticatorCallback, UserSession.SessionObserver {
    private final FactoryCredentialsHandler mHandlerFactory;
    private final FactoryCredentialsAuthenticator mAuthenticatorFactory;

    private final AuthenticationSuite mAuth;
    private final CurrentScreen mScreen;
    private final LaunchableConfig mProfileEditor;
    private final LaunchableConfig mFeed;
    private final LoginListener mListener;
    private CredentialsHandler mHandler;

    private boolean mAuthenticating = false;

    public interface LoginListener {
        void onSignUpRequested(@Nullable AuthenticatedUser user, String message);

        void onAuthenticated();

        void onAuthenticationFailed(AuthenticationError error);

        void onNoCurrentUser();

        void onLoggedIn();
    }

    private PresenterLogin(AuthenticationSuite auth,
                           LaunchableConfig profileEditor,
                           LaunchableConfig feed,
                           CurrentScreen screen,
                           FactoryCredentialsHandler handlerFactory,
                           FactoryCredentialsAuthenticator authenticatorFactory,
                           LoginListener listener) {
        mAuth = auth;
        mProfileEditor = profileEditor;
        mFeed = feed;
        mScreen = screen;
        mHandlerFactory = handlerFactory;
        mAuthenticatorFactory = authenticatorFactory;
        mListener = listener;
    }

    public void startSessionObservation() {
        getUserSession().registerSessionObserver(this);
    }

    private UserSession getUserSession() {
        return mAuth.getUserSession();
    }

    @NonNull
    public String getSignUpMessage() {
        return Strings.Alerts.NEW_USER;
    }

    public boolean hasAuthenticatedUser() {
        return getUserSession().isAuthenticated();
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

    private void authenticationFinished() {
        mAuthenticating = false;
        mHandler = null;
    }

    public void signUpNewAuthor(String email) {
        try {
            launchProfileEditor(new ProfileArgs(new EmailAddress(email)));
        } catch (EmailAddressException e) {
            launchProfileEditor(new ProfileArgs());
        }
    }

    public void signUpNewAuthor(AuthenticatedUser user) {
        launchProfileEditor(new ProfileArgs(user));
    }

    public void onLoginComplete() {
        getUserSession().unregisterSessionObserver(this);
        mListener.onLoggedIn();
        mFeed.launch();
        mScreen.close();
    }

    public EmailValidation validateEmail(String email) {
        return new EmailValidation(email);
    }

    public PasswordValidation validatePassword(String password) {
        return new PasswordValidation(password);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mProfileEditor.getDefaultRequestCode()) {
            mScreen.showToast(Strings.Toasts.COMPLETING_SIGNUP);
            if(data != null) {
                EmailPassword emailPassword = data.getParcelableExtra(PresenterProfile.EMAIL_PASSWORD);
                if (emailPassword != null) authenticateWithCredentials(emailPassword);
            } else {
                getUserSession().refreshSession();
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
    public void onLogOut(UserAccount account, CallbackMessage message) {

    }

    @Override
    public void onAuthenticationError(AuthenticationError error) {
        resolveError(null, error);
        authenticationFinished();
    }

    private void launchProfileEditor(ProfileArgs profileArgs) {
        Bundle args = new Bundle();
        ParcelablePacker<ProfileArgs> packer = new ParcelablePacker<>();
        packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, profileArgs, args);
        mProfileEditor.launch(new UiLauncherArgs(mProfileEditor.getDefaultRequestCode()).setBundle(args));
    }

    private void resolveError(@Nullable AuthenticatedUser user, AuthenticationError error) {
        if (mListener != null) {
            if (error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
                mListener.onSignUpRequested(user, getSignUpMessage());
            } else if (error.is(AuthenticationError.Reason.NO_AUTHENTICATED_USER)) {
                mListener.onNoCurrentUser();
            } else {
                mScreen.showToast(Strings.Toasts.LOGIN_UNSUCCESSFUL + ": " + error);
                mListener.onAuthenticationFailed(error);
            }
        }
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
        public PresenterLogin build(ApplicationInstance app, LoginListener listener) {
            AuthenticationSuite auth = app.getAuthentication();
            UiSuite ui = app.getUi();
            UiConfig config = ui.getConfig();

            return new PresenterLogin(auth, config.getProfileEditor(), config.getFeed(),
                    ui.getCurrentScreen(), new FactoryCredentialsHandler(),
                    new FactoryCredentialsAuthenticator(auth.getAuthenticator()), listener);
        }
    }

}
