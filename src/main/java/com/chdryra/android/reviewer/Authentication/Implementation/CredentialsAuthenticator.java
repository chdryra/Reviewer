/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 28/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CredentialsAuthenticator<T> implements CredentialsCallback<T> {
    private final Authenticator<T> mAuthenticator;
    private final Authenticator.Callback mCallback;

    public CredentialsAuthenticator(Authenticator<T> authenticator, Authenticator.Callback callback) {
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
