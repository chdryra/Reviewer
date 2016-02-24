/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentFacebookLogin extends Fragment{
    private static final int LAYOUT = R.layout.facebook_login;
    private static final int LOGIN = R.id.loginbutton_facebook;

    private CallbackManager mCallbackManager;
    private FacebookLoginListener mListener;

    public interface FacebookLoginListener {
        void onSuccess(LoginResult loginResult);
        void onError(FacebookException error);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);

        LoginButton button = (LoginButton) view.findViewById(LOGIN);
        button.setFragment(this);
        button.setPublishPermissions(PlatformFacebook.PUBLISH_PERMISSION);
        

        mCallbackManager = CallbackManager.Factory.create();
        try {
            mListener = (FacebookLoginListener) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity should be a FacebookLoginListener!", e);
        }

        button.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mListener.onSuccess(loginResult);
            }

            @Override
            public void onCancel() {
                int x = 0;
            }

            @Override
            public void onError(FacebookException error) {
                mListener.onError(error);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
