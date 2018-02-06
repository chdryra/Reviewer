/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

import com.chdryra.android.startouch.Utils.EmailAddress;
import com.chdryra.android.startouch.Utils.EmailAddressException;

/**
 * Created by: Rizwan Choudrey
 * On: 10/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailValidation {
    private EmailAddress mIfValid;
    private EmailError mEmailError = EmailError.OK;

    public enum EmailError {
        OK("Ok"),
        INVALID_EMAIL("Email is invalid");

        private final String mMessage;

        EmailError(String message) {
            mMessage = message;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    public EmailValidation(String email) {
        try {
            mIfValid = new EmailAddress(email);
        } catch (EmailAddressException e) {
            mEmailError = EmailError.INVALID_EMAIL;
        }
    }

    public boolean isValid() {
        return mEmailError == EmailError.OK;
    }

    public EmailError getError() {
        return mEmailError;
    }

    public EmailAddress getEmailAddress() {
        return mIfValid;
    }
}
