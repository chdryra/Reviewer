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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryCredentialsAuthenticator;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryCredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.EmailValidation;
import com.chdryra.android.reviewer.Authentication.Implementation.PasswordValidation;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailAddressException;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Implementation.SignUpArgs;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterLogin implements ActivityResultListener, AuthenticatorCallback,
        UserAccounts.GetProfileCallback {
    private static final int FEED = RequestCodeGenerator.getCode("FeedScreen");
    private static final int SIGN_UP = RequestCodeGenerator.getCode("SignUpScreen");

    private FactoryCredentialsHandler mHandlerFactory;
    private FactoryCredentialsAuthenticator mAuthenticatorFactory;

    private ApplicationInstance mApp;
    private Activity mActivity;
    private CredentialsHandler mHandler;
    private LoginListener mListener;

    private boolean mAuthenticating = false;

    public interface LoginListener {
        void onSignUpRequested(@Nullable AuthenticatedUser user, String message);

        void onAuthenticated(AuthorProfile profile);

        void onAuthenticationFailed(AuthenticationError error);
    }

    private PresenterLogin(ApplicationInstance app,
                           Activity activity,
                           FactoryCredentialsHandler handlerFactory,
                           FactoryCredentialsAuthenticator authenticatorFactory,
                           LoginListener listener) {
        mApp = app;
        mActivity = activity;
        mHandlerFactory = handlerFactory;
        mAuthenticatorFactory = authenticatorFactory;
        mListener = listener;
    }

    @NonNull
    public String getSignUpMessage() {
        return "Looks like you're a new user?";
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

    public void onAuthorAuthenticated(AuthorProfile profile) {
        mApp.setAuthor(profile.getAuthor());
        launchLaunchable(mActivity, mApp.getConfigUi().getFeedConfig(), FEED, new Bundle());
        mActivity.finish();
    }

    public EmailValidation validateEmail(String email) {
        return new EmailValidation(email);
    }

    public PasswordValidation validatePassword(String password) {
        return new PasswordValidation(password);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHandler != null) mHandler.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAuthenticated(AuthenticatedUser user) {
        authenticationFinished();
        UserAccounts accounts = mApp.getUsersManager().getAccounts();
        accounts.getProfile(user, this);
    }

    @Override
    public void onProfile(AuthenticatedUser user, AuthorProfile profile, @Nullable
    AuthenticationError error) {
        if (error == null) {
            mListener.onAuthenticated(profile);
        } else {
            resolveError(user, error);
        }
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
        launchLaunchable(mActivity, mApp.getConfigUi().getSignUpConfig(), SIGN_UP, args);
    }

    private void resolveError(@Nullable AuthenticatedUser user, AuthenticationError error) {
        if (error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
            mListener.onSignUpRequested(user, getSignUpMessage());
        } else {
            mListener.onAuthenticationFailed(error);
        }
    }

    private void launchLaunchable(Activity activity, LaunchableConfig launchable, int code,
                                  Bundle args) {
        LaunchableUiLauncher uiLauncher = mApp.getUiLauncher();
        uiLauncher.launch(launchable, activity, code, args);
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

        public PresenterLogin build(Activity activity, LoginListener listener) {
            return new PresenterLogin(mApp, activity, new FactoryCredentialsHandler(),
                    new FactoryCredentialsAuthenticator(mApp.getUsersManager().getAuthenticator()
                    ), listener);
        }
    }

}
