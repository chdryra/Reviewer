/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.SessionProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.NamedAuthor;

/**
 * Created by: Rizwan Choudrey
 * On: 16/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserSession extends UserAuthenticator.UserStateObserver {
    interface SessionObserver {
        void onLogIn(@Nullable UserAccount account,
                     @Nullable AuthenticationError error);
    }

    boolean isAuthenticated();

    void setSessionObserver(SessionObserver observer);

    void unsetSessionObserver();

    void refreshSession();

    AuthorId getAuthorId();

    NamedAuthor getAuthor();

    UserAccount getUserAccount();

    void logout(SessionProvider.LogoutCallback callback, SessionProvider<?> googleHack);
}
