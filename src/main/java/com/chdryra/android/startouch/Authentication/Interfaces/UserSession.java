/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasAuthorId;

/**
 * Created by: Rizwan Choudrey
 * On: 16/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserSession extends UserAuthenticator.UserStateObserver, HasAuthorId {
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

    AuthenticatedUser getUser();

    UserAccount getAccount();

    void logout(LoginProvider<?> googleHack);
}
