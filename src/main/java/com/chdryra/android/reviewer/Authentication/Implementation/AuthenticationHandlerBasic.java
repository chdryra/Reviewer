/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.BinaryResultCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class AuthenticationHandlerBasic<T extends BinaryResultCallback>
        implements AuthenticatorCallback {
    private AuthenticationProvider<T> mProvider;
    private AuthenticatorCallback mCallback;

    protected abstract T getProviderCallback();

    public AuthenticationHandlerBasic(AuthenticationProvider<T> provider, AuthenticatorCallback callback) {
        mProvider = provider;
        mCallback = callback;
    }

    String getProviderName() {
        return mProvider.getName();
    }

    public AuthenticationProvider<T> getProvider() {
        return mProvider;
    }

    public void requestAuthentication() {
        mProvider.requestAuthentication(getProviderCallback());
    }

    @Override
    public void onSuccess(String provider, CallbackMessage result) {
        mCallback.onSuccess(provider, result);
    }

    @Override
    public void onFailure(String provider, CallbackMessage result) {
        mCallback.onFailure(provider, result);
    }
}
