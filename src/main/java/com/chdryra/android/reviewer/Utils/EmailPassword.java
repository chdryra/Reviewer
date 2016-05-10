/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Utils;

/**
 * Created by: Rizwan Choudrey
 * On: 28/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailPassword {
    private final EmailAddress mEmail;
    private final Password mPassword;

    public EmailPassword(EmailAddress email, Password password) {
        mEmail = email;
        mPassword = password;
    }

    public EmailAddress getEmail() {
        return mEmail;
    }

    public Password getPassword() {
        return mPassword;
    }
}
