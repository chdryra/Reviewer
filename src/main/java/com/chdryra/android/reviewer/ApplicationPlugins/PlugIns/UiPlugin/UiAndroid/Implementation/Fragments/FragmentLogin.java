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

import com.chdryra.android.mygenerallibrary.Dialogs.AlertListener;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Application.Implementation.AppInstanceAndroid;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.Application.Interfaces.CurrentScreen;
import com.chdryra.android.reviewer.Application.Interfaces.UiSuite;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.LoginProviders.FactorySessionProviders;
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
public class FragmentLogin extends Fragment implements PresenterLogin.LoginListener, AlertListener {
    private static final int SIGN_UP = RequestCodeGenerator.getCode("SignUp");
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
    private FactorySessionProviders mLoginProviders;

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

        ApplicationInstance app = getApp();
        mPresenter = new PresenterLogin.Builder().build(app, this);
        mPresenter.startSessionObservation();
        mLoginProviders = new FactorySessionProviders();

        bindButtonsToProviders(facebookButton, googleButton, twitterButton, emailButton);

        if (checkInternet() && mPresenter.hasAuthenticatedUser()) showLoggingInDialog();

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
    public void onNewAccount(@Nullable AuthenticatedUser user) {
        closeLoggingInDialog();
        if (user == null) {
            UiSuite ui = getApp().getUi();
            ui.getCurrentScreen().showAlert(Strings.Alerts.SIGN_UP, SIGN_UP, this, new Bundle());
        } else {
            mPresenter.launchMakeProfile(user);
            mEmailSignUp = false;
        }
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
    public void onLoggedIn() {
        closeLoggingInDialog();
    }

    @Override
    public void onAlertNegative(int requestCode, Bundle args) {
        mEmailSignUp = false;
    }

    @Override
    public void onAlertPositive(int requestCode, Bundle args) {
        mEmailSignUp = true;
        mPasswordConfirm.setVisibility(View.VISIBLE);
        mPasswordConfirm.setHint(Strings.EditTexts.Hints.CONFIRM_PASSWORD);
        makeToast(Strings.Toasts.CONFIRM_PASSWORD);
    }

    private AppInstanceAndroid getApp() {
        return AppInstanceAndroid.getInstance(getActivity());
    }

    private boolean checkInternet() {
        return getApp().getNetwork().isOnline(getCurrentScreen());
    }

    private void showLoggingInDialog() {
        mProgress = ProgressDialog.show(getActivity(), Strings.ProgressBar.LOGGING_IN,
                Strings.ProgressBar.PLEASE_WAIT, true);
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
        if (checkInternet()) {
            showLoggingInDialog();
            mPresenter.logIn(mLoginProviders.newFacebookLogin(this));
        }
    }

    private void attemptTwitterLogin() {
        if (checkInternet()) {
            showLoggingInDialog();
            mPresenter.logIn(mLoginProviders.newTwitterLogin(this));
        }
    }

    private void attemptGoogleLogin() {
        if (checkInternet()) {
            showLoggingInDialog();
            mPresenter.logIn(mLoginProviders.newGoogleLogin(this));
        }
    }

    private void attemptEmailPasswordLogin() {
        if (!checkInternet()) return;

        EmailAddress email = validateEmail();
        if (email != null) {
            showLoggingInDialog();
            EmailPassword ep = new EmailPassword(email, new Password(mPassword.getText().toString()));
            if(mEmailSignUp) {
                mPresenter.createEmailPasswordUser(ep);
            } else {
                attemptEmailPasswordLogin(ep);
            }
        }
    }

    private void attemptEmailPasswordLogin(EmailPassword ep) {
        mPresenter.logIn(mLoginProviders.newEmailPasswordLogin(ep));
    }

    @Nullable
    private EmailAddress validateEmail() {
        EmailValidation emailValid = mPresenter.validateEmail(mEmail.getText().toString());
        if(!emailValid.isValid()) {
            makeToast(emailValid.getError().getMessage());
            return null;
        } else {
            return emailValid.getEmailAddress();
        }
    }

    private void makeToast(String toast) {
        getCurrentScreen().showToast(toast);
    }

    private CurrentScreen getCurrentScreen() {
        return getApp().getUi().getCurrentScreen();
    }
}
