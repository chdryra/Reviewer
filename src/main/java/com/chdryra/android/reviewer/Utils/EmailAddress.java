/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Utils;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 24/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailAddress {

    private String mEmail;

    public EmailAddress(String email) throws EmailAddressException{
        boolean valid = EmailValidator.getInstance().isValid(email);
        if(!valid) throw new IllegalArgumentException("Not a valid email: " + email);
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }
}
