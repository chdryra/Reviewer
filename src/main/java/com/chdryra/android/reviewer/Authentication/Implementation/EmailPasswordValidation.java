/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.Utils.Password;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailPasswordValidation {
    private EmailPassword mIfValid;
    private Error mError;

    public enum Reason {OK, INVALID_EMAIL, INVALID_PASSWORD}

    public EmailPasswordValidation(String email, String password) {
        validate(email, password);
    }

    private void validate(String email, String password) {
        EmailValidation emailValid = new EmailValidation(email);
        EmailAddress address = emailValid.getEmailAddress();
        if(address == null) {
            mError = new Error(Reason.INVALID_EMAIL, emailValid.getError().getMessage());
            return;
        }

        PasswordValidation passwordValid = new PasswordValidation(password);
        Password pw = passwordValid.getPassword();
        if(pw == null) {
            mError = new Error(Reason.INVALID_PASSWORD, passwordValid.getError().getMessage());
            return;
        }

        mIfValid = new EmailPassword(address, pw);
    }

    public Error getError() {
        return mError;
    }

    public EmailPassword getEmailPassword() {
        return mIfValid;
    }

    public static class Error {
        private final Reason mReason;
        private final String mMessage;

        public Error(Reason reason, String message) {
            mReason = reason;
            mMessage = message;
        }

        public Reason getReason() {
            return mReason;
        }

        public String getMessage() {
            return mMessage;
        }
    }
}
