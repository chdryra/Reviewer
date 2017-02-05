/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorNameValidation;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PresenterProfile implements UserAccounts.CreateAccountCallback, UserAccounts.UpdateProfileCallback {
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

    public interface ProfileListener {
        void onProfileFetched(AuthorProfileSnapshot profile, CallbackMessage message);

        void onProfileCreated(AuthorProfileSnapshot profile, CallbackMessage message);

        void onProfileUpdated(@Nullable AuthorProfileSnapshot newProfile, CallbackMessage message);
    }

    private PresenterProfile(UserAccounts accounts, ProfileListener listener) {
        mAccounts = accounts;
        mListener = listener;
    }

    public void newAccount(AuthenticatedUser user, String name, @Nullable Bitmap photo) {
        AuthorNameValidation validation = new AuthorNameValidation(name);
        String author = validation.getName();
        if (author == null) {
            notifySignUpError(getError(validation.getReason()));
        } else {
            createAccount(user, author, photo);
        }
    }

    public void getProfile(UserAccount account) {
        account.getAuthorProfile().getProfileSnapshot(new AuthorProfile.ProfileCallback() {
            @Override
            public void onProfile(AuthorProfileSnapshot profile, CallbackMessage message) {
                mListener.onProfileFetched(profile, message);
            }
        });
    }

    public void updateProfile(UserAccount account, AuthorProfileSnapshot oldProfile, AuthorProfileSnapshot newProfile) {
        mAccounts.updateProfile(account, oldProfile, newProfile, this);
    }

    @Override
    public void onAccountUpdated(AuthorProfileSnapshot profile, @Nullable AuthenticationError error) {
        CallbackMessage message = CallbackMessage.ok();
        if(error != null) message = CallbackMessage.error(error.getMessage());
        mListener.onProfileUpdated(profile, message);
    }

    @Override
    public void onAccountCreated(UserAccount account, @Nullable AuthenticationError error) {
        account.getAuthorProfile().getProfileSnapshot(new AuthorProfile.ProfileCallback() {
            @Override
            public void onProfile(AuthorProfileSnapshot profile, CallbackMessage message) {
                mListener.onProfileCreated(profile, message);
            }
        });
    }

    private void notifySignUpError(AuthenticationError error) {
        mListener.onProfileUpdated(null, CallbackMessage.error(error.getMessage()));
    }

    private void createAccount(AuthenticatedUser user, String author, @Nullable Bitmap photo) {
        mAccounts.createAccount(user, mAccounts.newProfile(author, photo), this);
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
