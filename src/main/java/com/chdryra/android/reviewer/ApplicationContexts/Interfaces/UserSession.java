/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.SessionProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 16/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserSession extends UserAuthenticator.UserStateObserver {
    interface LoginObserver {
        void onLoggedIn(@Nullable AuthenticatedUser user,
                        @Nullable AuthorProfile profile,
                        @Nullable AuthenticationError error);
    }

    boolean hasUser();

    void unsetLoginObserver();

    boolean setLoginObserver(LoginObserver observer);

    DataAuthor getCurrentUserAsAuthor();

    void loginComplete(@Nullable SessionProvider<?> provider);

    void logout(SessionProvider.LogoutCallback callback);

    boolean getCurrentProfile(UserAccounts.GetProfileCallback callback);

    @Override
    void onUserStateChanged(@Nullable AuthenticatedUser oldUser,
                            @Nullable AuthenticatedUser newUser);
}
