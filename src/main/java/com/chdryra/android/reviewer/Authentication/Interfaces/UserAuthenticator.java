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
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UserAuthenticator {
    interface UserStateObserver {
        void onUserChanged(@Nullable AuthenticatedUser oldUser, @Nullable AuthenticatedUser newUser);
    }

    void logout();

    @Nullable
    AuthenticatedUser getAuthenticatedUser();

    void authenticateUser(EmailPassword emailPassword, AuthenticatorCallback callback);

    void authenticateUser(AccessToken token, AuthenticatorCallback callback);

    void authenticateUser(TwitterSession session, AuthenticatorCallback callback);

    void authenticateUser(GoogleSignInAccount account, AuthenticatorCallback callback);

    void registerObserver(UserStateObserver observer);

    void unregisterObserver(UserStateObserver observer);
}
