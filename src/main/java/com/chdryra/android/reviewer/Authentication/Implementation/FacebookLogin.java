/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;


import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLoginListener;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 20/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FacebookLogin implements AuthenticationProvider<FacebookLoginListener> {
    public static final String NAME = "FacebookLogin";

    protected FacebookCallback<LoginResult> getAuthenticationCallback(final FacebookLoginListener
                                                                              listener) {
        return new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                listener.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                listener.onFailure(error);
            }
        };
    }

    @Override
    public abstract void requestAuthentication(FacebookLoginListener resultListener);

    @Override
    public String getName() {
        return NAME;
    }
}
