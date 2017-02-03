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
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.TagKeyGenerator;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorNameValidation;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;
import com.chdryra.android.reviewer.Authentication.Implementation.EmailPasswordValidation;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Utils.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterProfile implements UserAccounts.CreateAccountCallback, UserAccounts.UpdateProfileCallback {
    static final String EMAIL_PASSWORD = TagKeyGenerator.getKey(PresenterProfile.class,
            "EmailPassword");

    private static final String APP = ApplicationInstance.APP_NAME;
    private static final AuthenticationError INVALID_LENGTH = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_NAME, AuthorNameValidation.Reason.INVALID_LENGTH
            .getMessage());
    private static final AuthenticationError INVALID_CHARACTERS = new AuthenticationError(APP,
            AuthenticationError.Reason.INVALID_NAME, AuthorNameValidation.Reason
            .INVALID_CHARACTERS.getMessage());
    private static final AuthenticationError UNKNOWN_ERROR = new AuthenticationError(APP,
            AuthenticationError.Reason.UNKNOWN_ERROR);

    private final UserAccounts mAccounts;
    private final ProfileListener mListener;
    private EmailPassword mEmailPassword;

    public interface ProfileListener {
        void onSignUpComplete(@Nullable EmailPassword emailPassword, @Nullable AuthenticationError error);

        void onProfileUpdated(AuthorProfileSnapshot newProfile, @Nullable AuthenticationError error);
    }

    private PresenterProfile(UserAccounts accounts, ProfileListener listener) {
        mAccounts = accounts;
        mListener = listener;
    }

    public void signUpNewAuthor(AuthenticatedUser user, String name, @Nullable Bitmap photo) {
        AuthorNameValidation validation = new AuthorNameValidation(name);
        String author = validation.getName();
        if (author == null) {
            notifySignUpError(getError(validation.getReason()));
        } else {
            createAccount(user, author, photo);
        }
    }

    public void signUpNewAuthor(String email, String password, String name, @Nullable Bitmap photo) {
        EmailPasswordValidation epValidation = new EmailPasswordValidation(email, password);
        EmailPassword emailPassword = epValidation.getEmailPassword();
        if (emailPassword == null) {
            notifySignUpError(getError(epValidation.getError()));
            return;
        }

        AuthorNameValidation validation = new AuthorNameValidation(name);
        final String author = validation.getName();
        if (author == null) {
            notifySignUpError(getError(validation.getReason()));
            return;
        }

        mEmailPassword = emailPassword;
        createAccount(emailPassword, author, photo);
    }

    public void onSignUpComplete(@Nullable EmailPassword emailPassword, Activity activity) {
        if (emailPassword != null) {
            activity.setResult(Activity.RESULT_OK,
                    new Intent().putExtra(EMAIL_PASSWORD, emailPassword));
        }

        activity.finish();
    }

    public void updateProfile(UserAccount account, AuthorProfileSnapshot newProfile) {
        mAccounts.updateProfile(account, newProfile, this);
    }

    @Override
    public void onAccountUpdated(AuthorProfileSnapshot profile, @Nullable AuthenticationError error) {
        mListener.onProfileUpdated(profile, error);
    }

    @Override
    public void onAccountCreated(UserAccount account, @Nullable AuthenticationError error) {
        mListener.onSignUpComplete(mEmailPassword, error);
    }

    private void notifySignUpError(AuthenticationError error) {
        mListener.onSignUpComplete(null, error);
    }

    private void createAccount(AuthenticatedUser user, String author, @Nullable Bitmap photo) {
        mAccounts.createAccount(user, mAccounts.newProfile(author, photo), this);
    }

    private void createAccount(EmailPassword emailPassword, final String author, @Nullable final Bitmap photo) {
        mAccounts.createUser(emailPassword, new UserAccounts
                .CreateUserCallback() {
            @Override
            public void onUserCreated(AuthenticatedUser user, @Nullable AuthenticationError error) {
                if (error != null) {
                    notifySignUpError(error);
                } else {
                    createAccount(user, author, photo);
                }
            }
        });
    }

    private AuthenticationError getError(EmailPasswordValidation.Error error) {
        if (error.getReason().equals(EmailPasswordValidation.Reason.INVALID_EMAIL)) {
            return new AuthenticationError(APP,
                    AuthenticationError.Reason.INVALID_EMAIL, error.getMessage());
        } else if (error.getReason().equals(EmailPasswordValidation.Reason.INVALID_PASSWORD)) {
            return new AuthenticationError(APP,
                    AuthenticationError.Reason.INVALID_PASSWORD, error.getMessage());
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
        public PresenterProfile build(ApplicationInstance app, ProfileListener listener) {
            return new PresenterProfile(app.getAuthentication().getUserAccounts(), listener);
        }
    }
}
