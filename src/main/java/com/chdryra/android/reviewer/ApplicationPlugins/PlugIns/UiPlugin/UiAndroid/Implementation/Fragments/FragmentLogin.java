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
import android.widget.Button;
import android.widget.LinearLayout;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityUsersFeed;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Other.FacebookLoginAndroid;
import com.chdryra.android.reviewer.R;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentLogin extends Fragment {
    private static final int LAYOUT = R.layout.fragment_login;

    private static final int EMAIL_BUTTON = R.id.login_button_email;
    private static final int GOOGLE_BUTTON = R.id.login_button_google;
    private static final int FACEBOOK_BUTTON = R.id.login_button_facebook;
    private static final int TWITTER_BUTTON = R.id.login_button_twitter;

    private static final int EMAIL_LOGIN = R.id.login_email;
    private static final int GOOGLE_LOGIN = R.id.login_google;
    private static final int FACEBOOK_LOGIN = R.id.login_facebook;
    private static final int TWITTER_LOGIN = R.id.login_twitter;

    private Button mEmailButton;
    private Button mGoogleButton;
    private Button mFacebookButton;
    private Button mTwitterButton;

    private LinearLayout mEmailLogin;
    private SignInButton mGoogleLoginButton;
    private FacebookLoginAndroid mFacebookLogin;
    private TwitterLoginButton mTwitterLoginButton;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);
        initialiseFacebookLogin(view);

        mEmailLogin = (LinearLayout) view.findViewById(EMAIL_LOGIN);
        mGoogleLoginButton = (SignInButton) view.findViewById(GOOGLE_LOGIN);

        mTwitterLoginButton = (TwitterLoginButton) view.findViewById(TWITTER_LOGIN);

        mGoogleLoginButton.setVisibility(View.GONE);
        mTwitterLoginButton.setVisibility(View.GONE);

        mEmailButton = (Button) view.findViewById(EMAIL_BUTTON);
        mGoogleButton = (Button) view.findViewById(GOOGLE_BUTTON);
        mTwitterButton = (Button) view.findViewById(TWITTER_BUTTON);

        mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFeedScreen();
            }
        });

        mGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleLoginButton.performClick();
            }
        });


        mTwitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTwitterLoginButton.performClick();
            }
        });

        return view;
    }

    private void initialiseFacebookLogin(View view) {
        LoginButton facebookLoginButton = (LoginButton) view.findViewById(FACEBOOK_LOGIN);
        facebookLoginButton.setVisibility(View.GONE);
        mFacebookLogin = new FacebookLoginAndroid(facebookLoginButton);
        mFacebookButton = (Button) view.findViewById(FACEBOOK_BUTTON);
        mFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFacebookLogin.launchLogin();
            }
        });
    }

    private void launchFeedScreen() {
        Intent intent = new Intent(getActivity(), ActivityUsersFeed.class);
        startActivity(intent);
        getActivity().finish();
    }
}
