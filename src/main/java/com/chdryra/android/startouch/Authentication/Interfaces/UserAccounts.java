/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;


import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.startouch.Utils.EmailPassword;


/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserAccounts {
    interface GetAccountCallback {
        void onAccount(UserAccount account, @Nullable AuthenticationError error);
    }

    interface CreateUserCallback {
        void onUserCreated(AuthenticatedUser user, @Nullable AuthenticationError error);
    }

    interface UpdateProfileCallback {
        void onAccountUpdated(AuthorProfile profile, @Nullable AuthenticationError error);
    }

    interface CreateAccountCallback {
        void onAccountCreated(UserAccount account, AuthorProfile profile, @Nullable
                AuthenticationError error);
    }

    AuthorProfile newProfile(String name, @Nullable Bitmap photo);

    AuthorProfile newProfile(AuthorProfile oldProfile, @Nullable String name, @Nullable Bitmap
            photo);

    void createUser(EmailPassword emailPassword, CreateUserCallback callback);

    void createAccount(AuthenticatedUser authUser, AuthorProfile profile, CreateAccountCallback
            callback);

    void updateProfile(UserAccount account, AuthorProfile oldProfile, AuthorProfile newProfile,
                       UpdateProfileCallback callback);

    void getAccount(AuthenticatedUser authUser, GetAccountCallback callback);
}
