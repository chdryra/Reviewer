/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication;


import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 20/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class FacebookLogin implements FacebookCallback<LoginResult> {
    private LoginResultHandler mHandler;

    public abstract void launchLogin();

    public void setLoginResultHandler(LoginResultHandler handler) {
        mHandler = handler;
    }

    @Override
    public void onError(FacebookException error) {
        if(mHandler != null) mHandler.onSuccess(new LoginSuccess<>(error));
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        if(mHandler != null) mHandler.onSuccess(new LoginSuccess<>(loginResult));
    }
}
