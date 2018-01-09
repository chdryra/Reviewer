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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation
        .LoginProviders.FactoryLoginProviders;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.EmailValidation;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View.PresenterLogin;
import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.Utils.Password;

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

    private PresenterLogin mPresenter;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private ProgressDialog mProgress;
    private FactoryLoginProviders mLoginProviders;

    private boolean mEmailSignUp = false;

    public static FragmentLogin newInstance() {
        return new FragmentLogin();
    }

    public void closeDialogs() {
        closeLoggingInDialog();
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

        mEmail = emailLoginLayout.findViewById(EMAIL);
        mPassword = emailLoginLayout.findViewById(PASSWORD);
        mPasswordConfirm = emailLoginLayout.findViewById(PASSWORD_CONFIRM);
        mPasswordConfirm.setVisibility(View.GONE);

        mLoginProviders = new FactoryLoginProviders();
        mPresenter = new PresenterLogin.Builder().build(getApp(), this);
        mPresenter.startSessionObservation();

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
        closeLoggingInDialog();
    }

    @Override
    public void onNoCurrentUser() {
        if (mProgress != null) mProgress.setMessage(Strings.ProgressBar.NO_ONE_LOGGED_IN);
        closeLoggingInDialog();
    }

    @Override
    public void onLoggingIn() {
        mProgress = ProgressDialog.show(getActivity(), Strings.ProgressBar.LOGGING_IN,
                Strings.ProgressBar.PLEASE_WAIT, true);
    }

    @Override
    public void onLoggedIn() {
        closeLoggingInDialog();
    }

    @Override
    public void onNewAccount(@Nullable AuthenticatedUser user) {
        closeLoggingInDialog();
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

    private void closeLoggingInDialog() {
        if (mProgress != null) mProgress.dismiss();
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
        if(email == null) return;

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

        if(mEmailSignUp && !password.equals(passwordConfirm)) {
            makeToast("Passwords don't match...");
            return null;
        }

        return new Password(password);
    }

    private void makeToast(String toast) {
        getCurrentScreen().showToast(toast);
    }
}
