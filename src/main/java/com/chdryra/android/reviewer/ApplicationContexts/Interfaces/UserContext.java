/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 16/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserContext extends UserAuthenticator.UserStateObserver {
    interface LoginObserver {
        void onLoggedIn(@Nullable AuthenticatedUser user,
                        @Nullable AuthorProfile profile,
                        @Nullable AuthenticationError error);
    }

    void unsetLoginObserver();

    void setLoginObserver(LoginObserver observer);

    void observeCurrentUser();

    DataAuthor getCurrentUserAsAuthor();

    void logout(Activity activity);

    boolean getCurrentProfile(UserAccounts.GetProfileCallback callback);

    void logoutCurrentUser();

    @Override
    void onUserStateChanged(@Nullable AuthenticatedUser oldUser,
                            @Nullable AuthenticatedUser newUser);
}
