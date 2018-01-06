/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.LoginProviders;



import com.chdryra.android.reviewer.Authentication.Implementation.PasswordValidation;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPasswordLogin;
import com.chdryra.android.reviewer.Utils.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class EmailPasswordLoginAndroid implements EmailPasswordLogin{

    private static final String EMAIL_PASSWORD = "EmailPassword";
    private final EmailPassword mEmailPassword;

    public EmailPasswordLoginAndroid(EmailPassword emailPassword) {
        mEmailPassword = emailPassword;
    }

    @Override
    public String getName() {
        return EMAIL_PASSWORD;
    }

    @Override
    public void login(Callback loginCallback) {
        PasswordValidation pwValid = new PasswordValidation(mEmailPassword.getPassword().toString());
        if(pwValid.isValid()){
            loginCallback.onSuccess(mEmailPassword);
        } else {
            loginCallback.onFailure(pwValid.getError().getMessage());
        }
    }

    @Override
    public void logout(LogoutCallback callback) {

    }
}
