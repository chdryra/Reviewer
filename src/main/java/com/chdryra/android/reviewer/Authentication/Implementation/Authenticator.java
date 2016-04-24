/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.content.Intent;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationProvider;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class Authenticator implements AuthenticationCallback, ActivityResultListener{
    private AuthenticationCallback mListener;
    private Map<Provider, AuthenticationProvider<?>> mProvidersMap;
    private AuthenticationProvider mRequestedProvider;

    public enum Provider {EMAIL, GOOGLE, FACEBOOK, TWITTER};

    public Authenticator(EmailLogin emailLogin,
                         GoogleLogin googleLogin,
                         FacebookLogin facebookLogin,
                         TwitterLogin twitterLogin,
                         AuthenticationCallback listener) {
        mListener = listener;

        mProvidersMap = new HashMap<>();
        mProvidersMap.put(Provider.EMAIL, emailLogin);
        mProvidersMap.put(Provider.GOOGLE, googleLogin);
        mProvidersMap.put(Provider.FACEBOOK, facebookLogin);
        mProvidersMap.put(Provider.TWITTER, twitterLogin);
    }

    private void setListener(AuthenticationCallback listener) {
        mListener = listener;
    }

    public void requestAuthentication(Provider provider) {
        mRequestedProvider = mProvidersMap.get(provider);
        mRequestedProvider.requestAuthentication(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mRequestedProvider == mProvidersMap.get(Provider.FACEBOOK)) {

        }
    }

    @Override
    public void onSuccess(CallbackMessage result) {

    }

    @Override
    public void onFailure(CallbackMessage result) {

    }
}
