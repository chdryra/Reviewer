/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Other;


import android.app.Fragment;
import android.content.Intent;

import com.chdryra.android.reviewer.Authentication.Implementation.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLoginCallback;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

/**
 * Created by: Rizwan Choudrey
 * On: 20/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FacebookLoginAndroid extends FacebookLogin {
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;

    public FacebookLoginAndroid(LoginButton loginButton, Fragment fragment) {
        mLoginButton = loginButton;
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.setFragment(fragment);
        mLoginButton.setPublishPermissions(PlatformFacebook.REQUIRED_PERMISSION);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void requestAuthentication(FacebookLoginCallback resultListener) {
        setListener(resultListener);
        mLoginButton.registerCallback(mCallbackManager, getAuthenticationCallback());
        mLoginButton.performClick();
        //TODO change to login manager
        //LoginManager manager = LoginManager.getInstance();
    }
}
