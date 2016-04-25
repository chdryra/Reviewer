/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Implementation.EmailAuthenticator;
import com.chdryra.android.reviewer.Authentication.Implementation.EmailLogin;
import com.chdryra.android.reviewer.Authentication.Implementation.FacebookAuthenticator;
import com.chdryra.android.reviewer.Authentication.Implementation.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Implementation.GoogleAuthenticator;
import com.chdryra.android.reviewer.Authentication.Implementation.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Implementation.TwitterAuthenticator;
import com.chdryra.android.reviewer.Authentication.Implementation.TwitterLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthenticationHandler {
    public EmailAuthenticator newAuthenticator(EmailLogin provider, AuthenticatorCallback callback) {
        return new EmailAuthenticator(provider, callback);
    }

    public FacebookAuthenticator newAuthenticator(FacebookLogin provider, AuthenticatorCallback callback) {
        return new FacebookAuthenticator(provider, callback);
    }

    public GoogleAuthenticator newAuthenticator(GoogleLogin provider, AuthenticatorCallback callback) {
        return new GoogleAuthenticator(provider, callback);
    }

    public TwitterAuthenticator newAuthenticator(TwitterLogin provider, AuthenticatorCallback callback) {
        return new TwitterAuthenticator(provider, callback);
    }
}
