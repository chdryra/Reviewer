/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Other;


import android.widget.EditText;

import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.Password;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailPasswordEditTexts implements EmailPassword {
    private EditText mEmail;
    private EditText mPassword;

    public EmailPasswordEditTexts(EditText email, EditText password) {
        mEmail = email;
        mPassword = password;
    }

    @Override
    public EmailAddress getEmail() {
        return new EmailAddress(mEmail.getText().toString().trim());
    }

    @Override
    public Password getPassword() {
        return new Password(mPassword.getText().toString().trim());
    }

    public boolean validEmailPassword() {
        return validateEmail(mEmail.getText().toString().trim())
                && validatePassword(mPassword.getText().toString().trim());
    }

    private boolean validatePassword(String password) {
        return password != null && password.length() > 0;
    }

    private boolean validateEmail(String email) {
        return email != null && email.length() > 0;
    }
}
