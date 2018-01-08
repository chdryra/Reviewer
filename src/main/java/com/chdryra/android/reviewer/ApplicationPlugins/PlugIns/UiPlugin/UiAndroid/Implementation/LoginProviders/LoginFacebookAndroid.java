/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.LoginProviders;


import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Interfaces.LoginFacebook;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ActivityResultListener;
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
public class LoginFacebookAndroid
        implements LoginFacebook, ActivityResultListener, FacebookCallback<LoginResult> {
    private static final List<String> PERMISSIONS;

    static {
        PERMISSIONS = new ArrayList<>();
        PERMISSIONS.add(PERMISSION);
    }

    private final Fragment mFragment;
    private Callback mListener;
    private final CallbackManager mCallbackManager;

    public LoginFacebookAndroid(Fragment fragment) {
        mFragment = fragment;
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
    }

    public void setListener(Callback listener) {
        mListener = listener;
    }

    @Override
    public void login(Callback loginCallback) {
        setListener(loginCallback);
        LoginManager manager = LoginManager.getInstance();
        manager.logInWithPublishPermissions(mFragment, PERMISSIONS);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        if (mListener != null) mListener.onSuccess(loginResult);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        if (mListener != null) mListener.onFailure(error);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void logout(LogoutCallback callback) {
        LoginManager.getInstance().logOut();
        callback.onLoggedOut(CallbackMessage.ok());
    }
}
