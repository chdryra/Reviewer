/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;


import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;


/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserAccounts {
    interface CreateUserCallback {
        void onUserCreated(@Nullable AuthenticatedUser user, CallbackMessage message);
    }

    interface AddProfileCallback {
        void onProfileAdded(AuthorProfile profile, CallbackMessage message);
    }

    interface GetProfileCallback {
        void onProfile(AuthorProfile profile, CallbackMessage message);
    }

    void createUser(EmailPassword emailPassword, AddProfileCallback callback);

    void addProfile(AuthenticatedUser user, AuthorProfile profile, AddProfileCallback callback);

    void getProfile(AuthenticatedUser user, GetProfileCallback callback);
}
