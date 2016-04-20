/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Other.FacebookLoginAndroid;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Authentication.LoginResultHandler;
import com.facebook.login.widget.LoginButton;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentFacebookLogin extends Fragment{
    private static final int LAYOUT = R.layout.login_facebook;
    private static final int LOGIN = R.id.login_button_facebook;

    private FacebookLoginAndroid mLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);

        LoginButton button = (LoginButton) view.findViewById(LOGIN);
        LoginResultHandler handler;
        try {
            handler = (LoginResultHandler) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity should be a LoginResultHandler!", e);
        }

        mLogin = new FacebookLoginAndroid(button, handler);
        mLogin.setFragment(this);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLogin.onActivityResult(requestCode, resultCode, data);
    }

}
