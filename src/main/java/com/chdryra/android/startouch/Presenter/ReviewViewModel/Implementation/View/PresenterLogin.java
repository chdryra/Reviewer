/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.Dialogs.AlertListener;
import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.Application.Interfaces.AccountsSuite;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.Application.Interfaces.NetworkSuite;
import com.chdryra.android.startouch.Application.Interfaces.UiSuite;
import com.chdryra.android.startouch.Authentication.Factories.FactoryCredentialsHandler;
import com.chdryra.android.startouch.Authentication.Factories.FactoryCredentialsProvider;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.startouch.Authentication.Implementation.EmailValidation;
import com.chdryra.android.startouch.Authentication.Interfaces.CredentialsAuthenticator;
import com.chdryra.android.startouch.Authentication.Interfaces.CredentialsProvider;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginEmailPassword;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginFacebook;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginGoogle;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginTwitter;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccount;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.chdryra.android.startouch.Utils.EmailPassword;
import com.chdryra.android.startouch.Utils.ParcelablePacker;
import com.chdryra.android.startouch.View.Configs.Interfaces.LaunchableConfig;
import com.chdryra.android.startouch.View.Configs.Interfaces.UiConfig;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterLogin implements ActivityResultListener, CredentialsAuthenticator.Callback,
        UserSession.SessionObserver, AlertListener {
    private static final int SIGN_UP = RequestCodeGenerator.getCode("SignUp");

    private final AccountsSuite mAuth;
    private final NetworkSuite mNetwork;
    private final CurrentScreen mScreen;
    private final LaunchableConfig mProfileEditor;
    private final LaunchableConfig mFeed;
    private final FactoryCredentialsProvider mProviderFactory;
    private final FactoryCredentialsHandler mHandlerFactory;
    private final LoginListener mListener;

    private CredentialsProvider<?> mCredentialsProvider;
    private boolean mAuthenticating = false;

    public interface LoginListener {
        void onNewAccount(@Nullable AuthenticatedUser user);

        void onAuthenticationFailed(AuthenticationError error);

        void onNewUserCreated(EmailPassword emailPassword);

        void onNoCurrentUser();

        void onLoggedIn();

        void onLoggingIn();

        void onSignUp();
    }

    private PresenterLogin(AccountsSuite auth,
                           NetworkSuite network,
                           LaunchableConfig profileEditor,
                           LaunchableConfig feed,
                           CurrentScreen screen,
                           FactoryCredentialsProvider providerFactory,
                           FactoryCredentialsHandler handlerFactory,
                           LoginListener listener) {
        mAuth = auth;
        mNetwork = network;
        mProfileEditor = profileEditor;
        mFeed = feed;
        mScreen = screen;
        mProviderFactory = providerFactory;
        mHandlerFactory = handlerFactory;
        mListener = listener;
        getUserSession().registerSessionObserver(this);
    }

    public boolean isInSession() {
        return checkInternet() && getUserSession().isAuthenticated();
    }

    public void logIn(LoginEmailPassword login) {
        authenticate(mProviderFactory.newCredentialsProvider(login),
                mHandlerFactory.newEmailPasswordHandler(this));
    }

    public void logIn(LoginFacebook login) {
        authenticate(mProviderFactory.newCredentialsProvider(login),
                mHandlerFactory.newFacebookHandler(this));
    }

    public void logIn(LoginGoogle login) {
        authenticate(mProviderFactory.newCredentialsProvider(login),
                mHandlerFactory.newGoogleHandler(this));
    }

    public void logIn(LoginTwitter login) {
        authenticate(mProviderFactory.newCredentialsProvider(login),
                mHandlerFactory.newTwitterHandler(this));
    }

    public void createEmailPasswordUser(final EmailPassword emailPassword) {
        if (!checkInternet()) return;

        mListener.onLoggingIn();
        mAuth.getUserAccounts().createUser(emailPassword, new UserAccounts.CreateUserCallback() {
            @Override
            public void onUserCreated(AuthenticatedUser user, @Nullable AuthenticationError error) {
                if (error == null) {
                    mListener.onNewUserCreated(emailPassword);
                } else {
                    mListener.onAuthenticationFailed(error);
                }
            }
        });
    }

    public EmailValidation validateEmail(String email) {
        return new EmailValidation(email);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mProfileEditor.getDefaultRequestCode()
                && resultCode == Activity.RESULT_OK) {
            makeToast(Strings.Toasts.COMPLETING_SIGNUP);
            getUserSession().refreshSession();
        } else if (mCredentialsProvider != null) {
            mCredentialsProvider.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onLogIn(@Nullable UserAccount account,
                        @Nullable AuthenticationError error) {
        if (error == null && account != null) {
            onLoginComplete();
        } else {
            if (error == null) {
                error = new AuthenticationError(ApplicationInstance.APP_NAME, AuthenticationError
                        .Reason.UNKNOWN_ERROR);
            }
            resolveError(account != null ? account.getAccountHolder() : null, error);
        }
    }

    @Override
    public void onLogOut(UserAccount account, CallbackMessage message) {
        //login screen doesn't care about logout.
    }

    @Override
    public void onAuthenticated(AuthenticatedUser user) {
        //wait for onLogIn to be called as a SessionObserver
    }

    @Override
    public void onAuthenticationError(AuthenticationError error) {
        resolveError(null, error);
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {

    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        makeToast(Strings.Toasts.CONFIRM_PASSWORD);
        mListener.onSignUp();
    }

    private UserSession getUserSession() {
        return mAuth.getUserSession();
    }

    private void launchMakeProfile(AuthenticatedUser user) {
        Bundle args = new Bundle();
        ParcelablePacker<AuthenticatedUser> packer = new ParcelablePacker<>();
        packer.packItem(ParcelablePacker.CurrentNewDatum.CURRENT, user, args);
        mProfileEditor.launch(new UiLauncherArgs(mProfileEditor.getDefaultRequestCode())
                .setBundle(args));
    }

    private void makeToast(String completingSignup) {
        mScreen.showToast(completingSignup);
    }

    private void authenticationFinished() {
        mAuthenticating = false;
        mCredentialsProvider = null;
    }

    private void onLoginComplete() {
        authenticationFinished();
        getUserSession().unregisterSessionObserver(this);
        mListener.onLoggedIn();
        mFeed.launch();
        mScreen.close();
    }

    private void resolveError(@Nullable AuthenticatedUser user, AuthenticationError error) {
        if (mListener != null) {
            if (error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
                if (user == null) {
                    mScreen.showAlert(Strings.Alerts.SIGN_UP, SIGN_UP, this, new Bundle());
                } else {
                    launchMakeProfile(user);
                }
                mListener.onNewAccount(user);
            } else if (error.is(AuthenticationError.Reason.NO_AUTHENTICATED_USER)) {
                mListener.onNoCurrentUser();
            } else {
                makeToast(Strings.Toasts.LOGIN_UNSUCCESSFUL + ": " + error);
                mListener.onAuthenticationFailed(error);
            }
        }
        authenticationFinished();
    }

    private <Cred> void authenticate(CredentialsProvider<Cred> provider,
                                     CredentialsProvider.Callback<Cred> callback) {
        if (!mAuthenticating && checkInternet()) {
            mAuthenticating = true;
            mCredentialsProvider = provider;
            mListener.onLoggingIn();
            provider.requestCredentials(callback);
        }
    }

    private boolean checkInternet() {
        return mNetwork.isOnline(mScreen);
    }

    public static class Builder {
        public PresenterLogin build(ApplicationInstance app, LoginListener listener) {
            AccountsSuite auth = app.getAccounts();
            NetworkSuite net = app.getNetwork();
            UiSuite ui = app.getUi();
            UiConfig config = ui.getConfig();

            return new PresenterLogin(auth, net, config.getProfileEditor(), config.getFeed(),
                    ui.getCurrentScreen(),
                    new FactoryCredentialsProvider(),
                    new FactoryCredentialsHandler(auth.getAuthenticator()),
                    listener);
        }
    }

}
