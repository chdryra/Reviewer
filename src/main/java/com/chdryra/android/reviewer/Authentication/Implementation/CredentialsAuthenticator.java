/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 28/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CredentialsAuthenticator<T> implements CredentialsCallback<T> {
    private final AuthenticatorCallback mCallback;
    private AuthenticationCall<T> mCall;

    public interface AuthenticationCall<T> {
        void authenticate(T credentials, AuthenticatorCallback callback);
    }

    public CredentialsAuthenticator(AuthenticatorCallback callback, AuthenticationCall<T> call) {
        mCallback = callback;
        mCall = call;
    }

    public void authenticate(T credentials) {
        mCall.authenticate(credentials, mCallback);
    }

    @Override
    public void onCredentialsObtained(String provider, T credentials) {
        authenticate(credentials);
    }

    @Override
    public void onCredentialsFailure(String provider, AuthenticationError error) {
        mCallback.onAuthenticationError(error);
    }
}
