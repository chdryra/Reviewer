/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivitySocialAuthUi;
import com.chdryra.android.startouch.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentTwitterLogin extends Fragment {
    private static final int LAYOUT = R.layout.login_twitter;
    private static final int LOGIN = R.id.login_button_twitter;

    private TwitterLoginButton mLoginButton;
    private ActivitySocialAuthUi mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        mLoginButton = (TwitterLoginButton) view.findViewById(LOGIN);
        mLoginButton.setCallback(newTwitterLoginCallback());

        setListener();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void setListener() {
        try {
            mListener = (ActivitySocialAuthUi) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("Activity should be a LoginResultListener!", e);
        }
    }

    @NonNull
    private Callback<TwitterSession> newTwitterLoginCallback() {
        return new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                mListener.onSuccess(result);
            }

            @Override
            public void failure(TwitterException exception) {
                mListener.onFailure(exception);
            }
        };
    }
}
