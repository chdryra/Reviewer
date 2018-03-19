/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Utils.EmailPassword;
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
        void onUserStateChanged(@Nullable AuthenticatedUser oldUser, @Nullable AuthenticatedUser
                newUser);
    }

    void logout();

    @Nullable
    AuthenticatedUser getAuthenticatedUser();

    void authenticateUser(EmailPassword emailPassword, CredentialsAuthenticator.Callback callback);

    void authenticateUser(AccessToken token, CredentialsAuthenticator.Callback callback);

    void authenticateUser(TwitterSession session, CredentialsAuthenticator.Callback callback);

    void authenticateUser(GoogleSignInAccount account, CredentialsAuthenticator.Callback callback);

    void registerObserver(UserStateObserver observer);

    void unregisterObserver(UserStateObserver observer);
}
