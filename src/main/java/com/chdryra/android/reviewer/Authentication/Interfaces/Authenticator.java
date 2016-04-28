/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface Authenticator {
    void authenticateCredentials(EmailPassword emailPassword, AuthenticatorCallback callback);

    void authenticateCredentials(AccessToken token, AuthenticatorCallback callback);

    void authenticateCredentials(TwitterSession session, AuthenticatorCallback callback);

    void authenticateCredentials(GoogleSignInAccount account, AuthenticatorCallback callback);
}
