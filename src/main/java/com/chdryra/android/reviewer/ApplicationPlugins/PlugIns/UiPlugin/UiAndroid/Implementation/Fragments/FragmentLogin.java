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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Other.FacebookLoginAndroid;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthenticationHandler;
import com.chdryra.android.reviewer.Authentication.Implementation.Authenticator;
import com.chdryra.android.reviewer.Authentication.Implementation.EmailLogin;
import com.chdryra.android.reviewer.Authentication.Implementation.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Implementation.TwitterLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.Password;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentLogin extends Fragment implements AuthenticatorCallback {
    private static final int LAYOUT = R.layout.fragment_login;

    private static final int EMAIL_EDIT_TEXT = R.id.edit_text_login_email;
    private static final int PASSWORD_EDIT_TEXT = R.id.edit_text_login_password;
    private static final int EMAIL_BUTTON = R.id.login_button_email;

    private static final int GOOGLE_BUTTON = R.id.login_button_google;
    private static final int FACEBOOK_BUTTON = R.id.login_button_facebook;
    private static final int TWITTER_BUTTON = R.id.login_button_twitter;

    private static final int EMAIL_LOGIN = R.id.login_email;
    private static final int GOOGLE_LOGIN = R.id.login_google;
    private static final int FACEBOOK_LOGIN = R.id.login_facebook;
    private static final int TWITTER_LOGIN = R.id.login_twitter;

    private EditText mEmail;
    private EditText mPassword;

    private Authenticator mAuthenticator;

    private EmailLogin mEmailLogin;
    private GoogleLogin mGoogleLogin;
    private FacebookLoginAndroid mFacebookLogin;
    private TwitterLogin mTwitterLogin;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        LoginButton facebookLoginButton = (LoginButton) view.findViewById(FACEBOOK_LOGIN);
        facebookLoginButton.setVisibility(View.GONE);
        Button facebookButton = (Button) view.findViewById(FACEBOOK_BUTTON);

        SignInButton googleLoginButton = (SignInButton) view.findViewById(GOOGLE_LOGIN);
        TwitterLoginButton twitterLoginButton = (TwitterLoginButton) view.findViewById
                (TWITTER_LOGIN);
        googleLoginButton.setVisibility(View.GONE);
        twitterLoginButton.setVisibility(View.GONE);
        Button googleButton = (Button) view.findViewById(GOOGLE_BUTTON);
        Button twitterButton = (Button) view.findViewById(TWITTER_BUTTON);

        LinearLayout emailLoginLayout = (LinearLayout) view.findViewById(EMAIL_LOGIN);
        mEmail = (EditText) emailLoginLayout.findViewById(EMAIL_EDIT_TEXT);
        mPassword = (EditText) emailLoginLayout.findViewById(PASSWORD_EDIT_TEXT);
        Button emailButton = (Button) emailLoginLayout.findViewById(EMAIL_BUTTON);

        mEmailLogin = new EmailLogin(new EditTextEmailPwGetter(mEmail, mPassword));
        mGoogleLogin = new GoogleLogin();
        mFacebookLogin = new FacebookLoginAndroid(facebookLoginButton, this);
        mTwitterLogin = new TwitterLogin();

        mAuthenticator = new Authenticator(new FactoryAuthenticationHandler());

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validEmailPassword()) {
                    mAuthenticator.requestAuthentication(mEmailLogin, FragmentLogin.this);
                }
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthenticator.requestAuthentication(mFacebookLogin, FragmentLogin.this);
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthenticator.requestAuthentication(mGoogleLogin, FragmentLogin.this);
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthenticator.requestAuthentication(mTwitterLogin, FragmentLogin.this);
            }
        });

        return view;
    }

    private boolean validEmailPassword() {
        return validateEmail(mEmail.getText().toString().trim())
                && validatePassword(mPassword.getText().toString().trim());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAuthenticator.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(String provider, UserId id, CallbackMessage result) {
        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
        launchFeedScreen();
    }

    @Override
    public void onFailure(String provider, CallbackMessage result) {
        Toast.makeText(getActivity(), "Login unsuccessful: " + result.getMessage(), Toast
                .LENGTH_SHORT).show();
    }

    private boolean validatePassword(String password) {
        return password != null && password.length() > 0;
    }

    private boolean validateEmail(String email) {
        return email != null && email.length() > 0;
    }

    private void launchFeedScreen() {
        Intent intent = new Intent(getActivity(), ActivityUsersFeed.class);
        startActivity(intent);
        getActivity().finish();
    }

    private static class EditTextEmailPwGetter implements EmailLogin.EmailPasswordGetter {
        private EditText mEmail;
        private EditText mPassword;

        public EditTextEmailPwGetter(EditText email, EditText password) {
            mEmail = email;
            this.mPassword = password;
        }

        @Override
        public EmailAddress getEmail() {
            return new EmailAddress(mEmail.getText().toString().trim());
        }

            @Override
            public Password getPassword() {
                return new Password(mPassword.getText().toString().trim());
            }
    }
}
