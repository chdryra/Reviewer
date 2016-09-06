/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.SessionProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 16/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserSession extends UserAuthenticator.UserStateObserver {
    interface SessionObserver {
        void onLogIn(@Nullable UserAccount account,
                     @Nullable AuthenticationError error);

        void onLogOut(UserAccount account, CallbackMessage message);
    }

    boolean isAuthenticated();

    boolean isInSession();

    void registerSessionObserver(SessionObserver observer);

    void unregisterSessionObserver(SessionObserver observer);

    void refreshSession();

    AuthorId getAuthorId();

    UserAccount getAccount();

    void logout(SessionProvider<?> googleHack);
}
