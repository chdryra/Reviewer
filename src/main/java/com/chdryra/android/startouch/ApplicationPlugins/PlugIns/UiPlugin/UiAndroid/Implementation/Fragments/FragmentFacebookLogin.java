/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Activities.ActivitySocialAuthUi;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .LoginProviders.LoginFacebookAndroid;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginFacebook;
import com.chdryra.android.startouch.R;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentFacebookLogin extends Fragment {
    private static final int LAYOUT = R.layout.login_facebook;

    private LoginFacebookAndroid mLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        mLogin = new LoginFacebookAndroid(this);
        mLogin.setListener(getActivityAsListener());

        return inflater.inflate(LAYOUT, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLogin.onActivityResult(requestCode, resultCode, data);
    }

    @NonNull
    private LoginFacebook.Callback getActivityAsListener() {
        final ActivitySocialAuthUi activity;
        try {
            activity = (ActivitySocialAuthUi) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity should be ActivitySocialAuthUi!", e);
        }

        return new LoginFacebook.Callback() {
            @Override
            public void onSuccess(LoginResult result) {
                activity.onSuccess(result);
            }

            @Override
            public void onFailure(FacebookException result) {
                activity.onFailure(result);
            }
        };
    }
}
