/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

import com.chdryra.android.startouch.Utils.Password;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PasswordValidation {
    private static final int MIN_PASSWORD = 8;
    private Password mIfValid;
    private PasswordError mPasswordError = PasswordError.OK;

    public enum PasswordError {
        OK("Ok"),
        INVALID_PASSWORD("Password should have at least " + MIN_PASSWORD + " characters");

        private final String mMessage;

        PasswordError(String message) {
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    public PasswordValidation(String password) {
        if (password.length() < MIN_PASSWORD) {
            mPasswordError = PasswordError.INVALID_PASSWORD;
        } else {
            mIfValid = new Password(password);
        }
    }

    public boolean isValid() {
        return mPasswordError == PasswordError.OK;
    }

    public PasswordError getError() {
        return mPasswordError;
    }

    public Password getPassword() {
        return mIfValid;
    }
}
