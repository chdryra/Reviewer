/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

import android.content.Intent;

import com.chdryra.android.corelibrary.AsyncUtils.BinaryResultCallback;
import com.chdryra.android.startouch.Authentication.Interfaces.CredentialsProvider;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginProvider;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CredentialsProviderBasic<Cred, ProvCb extends BinaryResultCallback>
        implements CredentialsProvider<Cred> {
    private final LoginProvider<ProvCb> mProvider;

    private Callback<Cred> mCallback;

    CredentialsProviderBasic(LoginProvider<ProvCb> provider) {
        mProvider = provider;
    }

    protected abstract ProvCb getProviderCallback();

    void notifyOnSuccess(Cred credentials) {
        mCallback.onCredentialsObtained(new Credentials<>(mProvider.getName(), credentials));
    }

    void notifyOnFailure(String message) {
        mCallback.onCredentialsFailure(new AuthenticationError(mProvider.getName(),
                AuthenticationError.Reason.PROVIDER_ERROR, message));
    }

    @Override
    public void requestCredentials(Callback<Cred> callback) {
        mCallback = callback;
        mProvider.login(getProviderCallback());
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
