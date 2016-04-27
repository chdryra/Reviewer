/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class EmailAuthenticator {
    private final Authenticator mAuthenticator;
    private final AuthenticatorCallback mCallback;

    public EmailAuthenticator(Authenticator authenticator, AuthenticatorCallback callback) {
        mAuthenticator = authenticator;
        mCallback = callback;
    }

    public void authenticate(EmailPassword emailPassword) {
        mAuthenticator.authenticateEmailPasswordCredentials(emailPassword.getEmail().toString(),
                emailPassword.getPassword().toString(), mCallback);
    }
}
