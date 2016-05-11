/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailAddressException;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.Utils.EmailValidator;
import com.chdryra.android.reviewer.Utils.Password;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailPasswordValidation {
    private static final int MIN_PASSWORD = 8;
    private EmailPassword mIfValid;
    private Reason mReason = Reason.OK;

    public enum Reason {
        OK("Ok"),
        INVALID_EMAIL("Email is invalid"),
        INVALID_PASSWORD("Password should have at least " + MIN_PASSWORD + " characters");

        private String mMessage;

        Reason(String message) {
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    public EmailPasswordValidation(String email, String password) {
        boolean isEmailValid = EmailValidator.isValid(email);
        if (!isEmailValid) {
            mReason = Reason.INVALID_EMAIL;
        } else {
            if (password.length() < MIN_PASSWORD) mReason = Reason.INVALID_PASSWORD;
        }

        if (mReason == Reason.OK) {
            try {
                EmailAddress emailAddress = new EmailAddress(email);
                mIfValid = new EmailPassword(emailAddress, new Password(password));
            } catch (EmailAddressException e) {
                mReason = Reason.INVALID_EMAIL;
            }
        }
    }

    public Reason getReason() {
        return mReason;
    }

    public EmailPassword getEmailPassword() {
        return mIfValid;
    }
}
