/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;


import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;


/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserAccounts {
    interface CreateUserCallback {
        void onUserCreated(AuthenticatedUser user, @Nullable AuthenticationError error);
    }

    interface AddProfileCallback {
        void onProfileAdded(AuthenticatedUser user, AuthorProfile profile, @Nullable AuthenticationError error);
    }

    interface GetProfileCallback {
        void onProfile(AuthenticatedUser user, AuthorProfile profile, @Nullable AuthenticationError error);
    }

    void createUser(EmailPassword emailPassword, CreateUserCallback callback);

    void addProfile(AuthenticatedUser authUser, AuthorProfile profile, AddProfileCallback callback);

    void getProfile(AuthenticatedUser authUser, GetProfileCallback callback);
}
