/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailAddressException;

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

        private String mMessage;

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

    public EmailError getError() {
        return mEmailError;
    }

    @Nullable
    public EmailAddress getEmailAddress() {
        return mIfValid;
    }
}