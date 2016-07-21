/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.content.Intent;

import com.chdryra.android.reviewer.Authentication.Interfaces.BinaryResultCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Interfaces.SessionProvider;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CredentialsHandlerBasic<C, T extends BinaryResultCallback>
        implements CredentialsHandler {
    private SessionProvider<T> mProvider;
    private CredentialsCallback<C> mCallback;

    public CredentialsHandlerBasic(SessionProvider<T> provider, CredentialsCallback<C> callback) {
        mProvider = provider;
        mCallback = callback;
    }

    protected abstract T getProviderCallback();

    protected String getProviderName() {
        return mProvider.getName();
    }

    protected void notifyOnSuccess(String provider, C credentials) {
        mCallback.onCredentialsObtained(provider, credentials);
    }

    protected void notifyOnFailure(String provider, AuthenticationError error) {
        mCallback.onCredentialsFailure(provider, error);
    }

    @Override
    public void requestCredentials() {
        mProvider.requestSignIn(getProviderCallback());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            ActivityResultListener provider = (ActivityResultListener) mProvider;
            provider.onActivityResult(requestCode, resultCode, data);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
