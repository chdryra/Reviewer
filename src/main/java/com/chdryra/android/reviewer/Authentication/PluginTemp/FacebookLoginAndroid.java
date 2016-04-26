/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.PluginTemp;


import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLoginCallback;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 20/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FacebookLoginAndroid
        implements FacebookLogin, ActivityResultListener, FacebookCallback<LoginResult> {

    private static final String PERMISSION = PlatformFacebook.REQUIRED_PERMISSION;
    private static final List<String> PERMISSIONS;
    static {
        PERMISSIONS = new ArrayList<>();
        PERMISSIONS.add(PERMISSION);
    }

    private Fragment mFragment;
    private FacebookLoginCallback mListener;
    private CallbackManager mCallbackManager;

    @Override
    public void requestAuthentication(FacebookLoginCallback resultListener) {
        setListener(resultListener);
        LoginManager manager = LoginManager.getInstance();
        manager.logInWithPublishPermissions(mFragment, PERMISSIONS);
    }

    public FacebookLoginAndroid(Fragment fragment) {
        mFragment = fragment;
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
    }

    public void setListener(FacebookLoginCallback listener) {
        mListener = listener;
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
