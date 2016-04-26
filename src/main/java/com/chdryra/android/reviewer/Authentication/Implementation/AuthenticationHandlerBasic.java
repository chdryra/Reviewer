/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.BinaryResultCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class AuthenticationHandlerBasic<T extends BinaryResultCallback> {
    private CredentialsProvider<T> mProvider;
    private AuthenticatorCallback mCallback;

    protected abstract T getProviderCallback();

    public AuthenticationHandlerBasic(CredentialsProvider<T> provider, AuthenticatorCallback callback) {
        mProvider = provider;
        mCallback = callback;
    }

    String getProviderName() {
        return mProvider.getName();
    }

    public CredentialsProvider<T> getProvider() {
        return mProvider;
    }

    public void requestAuthentication() {
        mProvider.requestCredentials(getProviderCallback());
    }

    protected void notifyOnSuccess(String provider) {
        mCallback.onSuccess(provider);
    }

    protected void notifyOnFailure(String provider, AuthenticationError error) {
        mCallback.onFailure(provider, error);
    }
}
