/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterCredentialsCallback;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TwitterAuthenticator implements TwitterCredentialsCallback {
    private final Authenticator mAuthenticator;
    private final AuthenticatorCallback mCallback;

    public TwitterAuthenticator(Authenticator authenticator, AuthenticatorCallback callback) {
        mAuthenticator = authenticator;
        mCallback = callback;
    }

    @Override
    public void onCredentialsObtained(String provider, TwitterSession credentials) {
        TwitterAuthToken token = credentials.getAuthToken();
        mAuthenticator.authenticateTwitterCredentials(token.token, token.secret,
                credentials.getUserId(), mCallback);
    }

    @Override
    public void onCredentialsFailure(String provider, AuthenticationError error) {
        mCallback.onFailure(provider, error);
    }
}
