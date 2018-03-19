/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chdryra.android.startouch.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Application.Interfaces.CurrentScreen;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .LoginProviders.FactoryLoginProviders;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.startouch.Authentication.Implementation.EmailValidation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.PresenterLogin;
import com.chdryra.android.startouch.R;
import com.chdryra.android.startouch.Utils.EmailAddress;
import com.chdryra.android.startouch.Utils.EmailPassword;
import com.chdryra.android.startouch.Utils.Password;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FragmentLogin extends Fragment implements PresenterLogin.LoginListener {
    private static final int LAYOUT = R.layout.fragment_login;

    private static final int EMAIL = R.id.edit_text_login_email;
    private static final int PASSWORD = R.id.edit_text_login_password;
    private static final int PASSWORD_CONFIRM = R.id.edit_text_login_password_confirm;
    private static final int EMAIL_BUTTON = R.id.login_button_email;

    private static final int GOOGLE_BUTTON = R.id.login_button_google;
    private static final int FACEBOOK_BUTTON = R.id.login_button_facebook;
    private static final int TWITTER_BUTTON = R.id.login_button_twitter;

    private static final int EMAIL_LOGIN = R.id.login_email;
    private static final int PROGRESS = R.id.progress_layout;
    private static final int PROGRESS_TEXT = R.id.progress_text_view;

    private PresenterLogin mPresenter;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private FrameLayout mProgress;
    private TextView mProgressText;
    private FactoryLoginProviders mLoginProviders;

    private boolean mEmailSignUp = false;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(LAYOUT, container, false);

        LinearLayout emailLoginLayout = view.findViewById(EMAIL_LOGIN);

        Button emailButton = emailLoginLayout.findViewById(EMAIL_BUTTON);
        Button facebookButton = view.findViewById(FACEBOOK_BUTTON);
        Button googleButton = view.findViewById(GOOGLE_BUTTON);
        Button twitterButton = view.findViewById(TWITTER_BUTTON);

        mProgress = view.findViewById(PROGRESS);
        mProgressText = mProgress.findViewById(PROGRESS_TEXT);
        hideProgress();

        mEmail = emailLoginLayout.findViewById(EMAIL);
        mPassword = emailLoginLayout.findViewById(PASSWORD);
        mPasswordConfirm = emailLoginLayout.findViewById(PASSWORD_CONFIRM);
        mPasswordConfirm.setVisibility(View.GONE);
        mLoginProviders = new FactoryLoginProviders();
        mPresenter = new PresenterLogin.Builder().build(getApp(), this);

        bindButtonsToProviders(facebookButton, googleButton, twitterButton, emailButton);

        if (mPresenter.isInSession()) onLoggingIn();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNewUserCreated(EmailPassword emailPassword) {
        attemptEmailPasswordLogin(emailPassword);
    }

    @Override
    public void onAuthenticationFailed(AuthenticationError error) {
        hideProgress();
    }

    @Override
    public void onNoCurrentUser() {
        hideProgress();
    }

    @Override
    public void onLoggingIn() {
        showProgress(Strings.ProgressBar.LOGGING_IN);
    }

    @Override
    public void onLoggedIn() {
        hideProgress();
    }

    @Override
    public void onNewAccount(@Nullable AuthenticatedUser user) {
        hideProgress();
    }

    @Override
    public void onSignUp() {
        mEmailSignUp = true;
        mPasswordConfirm.setVisibility(View.VISIBLE);
        mPasswordConfirm.setHint(Strings.EditTexts.Hints.CONFIRM_PASSWORD);
    }

    private AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private CurrentScreen getCurrentScreen() {
        return getApp().getUi().getCurrentScreen();
    }

    private void hideProgress() {
        mProgress.setVisibility(View.INVISIBLE);
    }

    private void showProgress(String progressText) {
        mProgress.setVisibility(View.VISIBLE);
        mProgressText.setText(progressText);
    }

    private void bindButtonsToProviders(Button facebookButton,
                                        Button googleButton,
                                        Button twitterButton,
                                        Button emailButton) {
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptEmailPasswordLogin();
            }
        });

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptFacebookLogin();
            }
        });

        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptGoogleLogin();
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptTwitterLogin();
            }
        });
    }

    private void attemptFacebookLogin() {
        mPresenter.logIn(mLoginProviders.newFacebookLogin(this));
    }

    private void attemptTwitterLogin() {
        mPresenter.logIn(mLoginProviders.newTwitterLogin(this));
    }

    private void attemptGoogleLogin() {
        mPresenter.logIn(mLoginProviders.newGoogleLogin(this));
    }

    private void attemptEmailPasswordLogin(EmailPassword ep) {
        mPresenter.logIn(mLoginProviders.newEmailPasswordLogin(ep));
    }

    private void attemptEmailPasswordLogin() {
        EmailAddress email = validateEmail();
        if (email == null) return;

        Password password = validatePassword();
        if (password != null) {
            EmailPassword ep = new EmailPassword(email, password);
            if (mEmailSignUp) {
                mPresenter.createEmailPasswordUser(ep);
            } else {
                attemptEmailPasswordLogin(ep);
            }
        }
    }

    @Nullable
    private EmailAddress validateEmail() {
        EmailValidation emailValid = mPresenter.validateEmail(mEmail.getText().toString());
        if (!emailValid.isValid()) {
            makeToast(emailValid.getError().getMessage());
            return null;
        } else {
            return emailValid.getEmailAddress();
        }
    }

    @Nullable
    private Password validatePassword() {
        String password = mPassword.getText().toString();
        String passwordConfirm = mPasswordConfirm.getText().toString();

        if (password.length() == 0) {
            makeToast("Please enter password...");
            return null;
        }

        if (mEmailSignUp && !password.equals(passwordConfirm)) {
            makeToast("Passwords don't match...");
            return null;
        }

        return new Password(password);
    }

    private void makeToast(String toast) {
        getCurrentScreen().showToast(toast);
    }
}
