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
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 20/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FacebookLoginAndroid extends FacebookLogin {
    private static final String PERMISSION = PlatformFacebook.REQUIRED_PERMISSION;
    private static final List<String> PERMISSIONS;
    static {
        PERMISSIONS = new ArrayList<>();
        PERMISSIONS.add(PERMISSION);
    }

    private Fragment mFragment;
    private CallbackManager mCallbackManager;

    public FacebookLoginAndroid(Fragment fragment) {
        mCallbackManager = CallbackManager.Factory.create();
        mFragment = fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void requestAuthentication(FacebookLoginCallback resultListener) {
        setListener(resultListener);
        LoginManager manager = LoginManager.getInstance();
        manager.registerCallback(mCallbackManager, getAuthenticationCallback());
        manager.logInWithPublishPermissions(mFragment, PERMISSIONS);
    }
}
