/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.LoginProviders;


import com.chdryra.android.startouch.Authentication.Implementation.PasswordValidation;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginEmailPassword;
import com.chdryra.android.startouch.Utils.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class LoginEmailPasswordAndroid implements LoginEmailPassword {

    private static final String EMAIL_PASSWORD = "EmailPassword";
    private final EmailPassword mEmailPassword;

    public LoginEmailPasswordAndroid(EmailPassword emailPassword) {
        mEmailPassword = emailPassword;
    }

    @Override
    public String getName() {
        return EMAIL_PASSWORD;
    }

    @Override
    public void login(Callback loginCallback) {
        PasswordValidation pwValid = new PasswordValidation(mEmailPassword.getPassword().toString
                ());
        if (pwValid.isValid()) {
            loginCallback.onSuccess(mEmailPassword);
        } else {
            loginCallback.onFailure(pwValid.getError().getMessage());
        }
    }

    @Override
    public void logout(LogoutCallback callback) {

    }
}
