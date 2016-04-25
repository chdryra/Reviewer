/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;


import android.content.Intent;

import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLoginCallback;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 20/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FacebookLogin implements AuthenticationProvider<FacebookLoginCallback>, ActivityResultListener{
    public static final String NAME = "FacebookLogin";
    private FacebookLoginCallback mListener;

    public void setListener(FacebookLoginCallback listener) {
        mListener = listener;
    }

    protected FacebookCallback<LoginResult> getAuthenticationCallback() {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(mListener != null) mListener.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                if(mListener != null) mListener.onFailure(error);
            }
        };
    }

    @Override
    public abstract void requestAuthentication(FacebookLoginCallback resultListener);

    @Override
    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    @Override
    public String getName() {
        return NAME;
    }
}
