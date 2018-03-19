/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

import com.chdryra.android.startouch.Authentication.Interfaces.CredentialsAuthenticator;
import com.chdryra.android.startouch.Authentication.Interfaces.CredentialsProvider;

/**
 * Created by: Rizwan Choudrey
 * On: 28/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CredentialsHandler<T> implements CredentialsProvider.Callback<T> {
    private final CredentialsAuthenticator<T> mAuthenticator;
    private final CredentialsAuthenticator.Callback mCallback;

    public CredentialsHandler(CredentialsAuthenticator<T> authenticator, CredentialsAuthenticator
            .Callback callback) {
        mCallback = callback;
        mAuthenticator = authenticator;
    }

    @Override
    public void onCredentialsObtained(Credentials<T> credentials) {
        mAuthenticator.authenticate(credentials.getCredentials(), mCallback);
    }

    @Override
    public void onCredentialsFailure(AuthenticationError error) {
        mCallback.onAuthenticationError(error);
    }
}
