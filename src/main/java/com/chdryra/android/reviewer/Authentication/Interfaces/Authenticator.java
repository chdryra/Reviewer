/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface Authenticator {
    void authenticateEmailPasswordCredentials(String email, String password, AuthenticatorCallback callback);

    void authenticateFacebookCredentials(String token, AuthenticatorCallback callback);

    void authenticateTwitterCredentials(String token, String secret, long userId, AuthenticatorCallback callback);

    void authenticateGoogleCredentials(String token, AuthenticatorCallback callback);
}
