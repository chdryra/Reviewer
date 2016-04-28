/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Activities.ActivityUsersFeed;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Other.EmailPasswordEditTexts;
import com.chdryra.android.reviewer.ApplicationSingletons.ApplicationInstance;
import com.chdryra.android.reviewer.Authentication.Implementation.PresenterAuthentication;
import com.chdryra.android.reviewer.Authentication.PluginTemp.FactoryCredentialProviders;
import com.chdryra.android.reviewer.R;
import com.google.android.gms.common.SignInButton;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentLogin extends Fragment implements PresenterAuthentication.AuthenticationListener {
    private static final int LAYOUT = R.layout.fragment_login;

    private static final int EMAIL_EDIT_TEXT = R.id.edit_text_login_email;
    private static final int PASSWORD_EDIT_TEXT = R.id.edit_text_login_password;
    private static final int EMAIL_BUTTON = R.id.login_button_email;

    private static final int GOOGLE_BUTTON = R.id.login_button_google;
    private static final int FACEBOOK_BUTTON = R.id.login_button_facebook;
    private static final int TWITTER_BUTTON = R.id.login_button_twitter;

    private static final int EMAIL_LOGIN = R.id.login_email;
    private static final int GOOGLE_LOGIN = R.id.login_google;
    private static final int TWITTER_LOGIN = R.id.login_twitter;

    private PresenterAuthentication mPresenter;
    private EmailPasswordEditTexts mEmailPassword;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        SignInButton googleLoginButton = (SignInButton) view.findViewById(GOOGLE_LOGIN);
        TwitterLoginButton twitterLoginButton = (TwitterLoginButton) view.findViewById
                (TWITTER_LOGIN);
        googleLoginButton.setVisibility(View.GONE);
        twitterLoginButton.setVisibility(View.GONE);

        LinearLayout emailLoginLayout = (LinearLayout) view.findViewById(EMAIL_LOGIN);

        Button emailButton = (Button) emailLoginLayout.findViewById(EMAIL_BUTTON);
        Button facebookButton = (Button) view.findViewById(FACEBOOK_BUTTON);
        Button googleButton = (Button) view.findViewById(GOOGLE_BUTTON);
        Button twitterButton = (Button) view.findViewById(TWITTER_BUTTON);

        EditText email = (EditText) emailLoginLayout.findViewById(EMAIL_EDIT_TEXT);
        EditText password = (EditText) emailLoginLayout.findViewById(PASSWORD_EDIT_TEXT);

        ApplicationInstance app = ApplicationInstance.getInstance(getActivity());
        mPresenter = new PresenterAuthentication.Builder(app).build(this);
        mEmailPassword = new EmailPasswordEditTexts(email, password);

        bindButtonsToProviders(facebookButton, googleButton, twitterButton, emailButton);

        return view;
    }

    public void cancelAuthentication() {
        if(mPresenter != null) mPresenter.authenticationFinished();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onUserUnknown() {
        Toast.makeText(getActivity(), "User unknown", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticated() {
        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
        launchFeedScreen();
    }

    @Override
    public void onAuthenticationFailed(CallbackMessage message) {
        Toast.makeText(getActivity(), "Login unsuccessful: " + message.getMessage(), Toast
                .LENGTH_SHORT).show();
    }

    private void bindButtonsToProviders(Button facebookButton, Button googleButton,
                                        Button twitterButton, Button emailButton) {
        final FactoryCredentialProviders providers = new FactoryCredentialProviders();

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmailPassword.validEmailPassword()) {
                    mPresenter.authenticate(mEmailPassword);
                }
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.authenticate(providers.newFacebookLogin(FragmentLogin.this));
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.authenticate(providers.newGoogleLogin(FragmentLogin.this));
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.authenticate(providers.newTwitterLogin(FragmentLogin.this));
            }
        });
    }

    private void launchFeedScreen() {
        Intent intent = new Intent(getActivity(), ActivityUsersFeed.class);
        startActivity(intent);
        getActivity().finish();
    }
}
