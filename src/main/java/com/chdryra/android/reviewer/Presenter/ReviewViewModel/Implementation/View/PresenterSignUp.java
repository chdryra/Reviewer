/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorNameValidation;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Implementation.EmailPasswordValidation;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterSignUp implements UserAccounts.AddProfileCallback {
    private static final int FEED = RequestCodeGenerator.getCode("FeedScreen");
    private static final String APP = "app";
    private static final AuthenticationError INVALID_LENGTH = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_NAME, AuthorNameValidation.Reason.INVALID_LENGTH
            .getMessage());
    private static final AuthenticationError INVALID_CHARACTERS = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_NAME, AuthorNameValidation.Reason
            .INVALID_CHARACTERS.getMessage());
    private static final AuthenticationError INVALID_EMAIL = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_EMAIL);
    private static final AuthenticationError INVALID_PASSWORD = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_PASSWORD);
    private static final AuthenticationError UNKNOWN_ERROR = new AuthenticationError(APP,
            AuthenticationError.Reason.UNKNOWN_ERROR);

    private static final AuthorProfile NULL_PROFILE = new AuthorProfile();
    private final ApplicationInstance mApp;
    private final SignUpListener mListener;

    public interface SignUpListener {
        void onSignUpComplete(AuthorProfile profile, @Nullable AuthenticationError error);
    }

    public PresenterSignUp(ApplicationInstance app, SignUpListener listener) {
        mApp = app;
        mListener = listener;
    }

    public void signUpNewAuthor(AuthenticatedUser user, String name) {
        AuthorNameValidation validation = new AuthorNameValidation(name);
        String author = validation.getName();
        if (author == null) {
            mListener.onSignUpComplete(new AuthorProfile(), getError(validation.getReason()));
            return;
        }

        addProfile(user, author);
    }

    public void signUpNewAuthor(String email, String password, String name) {
        EmailPasswordValidation epValidation = new EmailPasswordValidation(email, password);
        EmailPassword emailPassword = epValidation.getEmailPassword();
        if (emailPassword == null) {
            mListener.onSignUpComplete(NULL_PROFILE, getError(epValidation.getReason()));
            return;
        }

        AuthorNameValidation validation = new AuthorNameValidation(name);
        final String author = validation.getName();
        if (author == null) {
            mListener.onSignUpComplete(new AuthorProfile(), getError(validation.getReason()));
            return;
        }

        createUserWithProfile(emailPassword, author);
    }

    public void onSignUpSuccessful(AuthorProfile profile, Activity activity) {
        mApp.setAuthor(profile.getAuthor());
        LaunchableUiLauncher uiLauncher = mApp.getUiLauncher();
        uiLauncher.launch(mApp.getConfigUi().getFeedConfig(), activity, FEED, new Bundle());
    }

    @Override
    public void onProfileAdded(AuthenticatedUser user, AuthorProfile profile, @Nullable
    AuthenticationError error) {
        mListener.onSignUpComplete(profile, error);
    }

    private void addProfile(AuthenticatedUser user, String author) {
        UserAccounts accounts = mApp.getUsersManager().getAccounts();
        AuthorProfile authorProfile = accounts.newProfie(author);
        accounts.addProfile(user, authorProfile, this);
    }

    private void createUserWithProfile(EmailPassword emailPassword, final String author) {
        UserAccounts accounts = mApp.getUsersManager().getAccounts();
        accounts.createUser(emailPassword, new UserAccounts.CreateUserCallback() {
            @Override
            public void onUserCreated(AuthenticatedUser user, @Nullable AuthenticationError error) {
                if (error != null) {
                    mListener.onSignUpComplete(NULL_PROFILE, error);
                } else {
                    addProfile(user, author);
                }
            }
        });
    }

    private AuthenticationError getError(EmailPasswordValidation.Reason reason) {
        if (reason.equals(EmailPasswordValidation.Reason.INVALID_EMAIL)) {
            return INVALID_EMAIL;
        } else if (reason.equals(EmailPasswordValidation.Reason.INVALID_PASSWORD)) {
            return INVALID_PASSWORD;
        } else {
            return UNKNOWN_ERROR;
        }
    }

    private AuthenticationError getError(AuthorNameValidation.Reason reason) {
        if (reason.equals(AuthorNameValidation.Reason.INVALID_LENGTH)) {
            return INVALID_LENGTH;
        } else if (reason.equals(AuthorNameValidation.Reason.INVALID_CHARACTERS)) {
            return INVALID_CHARACTERS;
        } else {
            return UNKNOWN_ERROR;
        }
    }

    public static class Builder {
        private ApplicationInstance mApp;

        public Builder(ApplicationInstance app) {
            mApp = app;
        }

        public PresenterSignUp build(SignUpListener listener) {
            return new PresenterSignUp(mApp, listener);
        }
    }
}
