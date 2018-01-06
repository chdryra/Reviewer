/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Implementation.CredentialsProviderEmailPassword;
import com.chdryra.android.reviewer.Authentication.Implementation.CredentialsProviderFacebook;
import com.chdryra.android.reviewer.Authentication.Implementation.CredentialsProviderGoogle;
import com.chdryra.android.reviewer.Authentication.Implementation.CredentialsProviderTwitter;
import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPasswordLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryCredentialsProvider {
    private final FactoryCredentialsAuthenticator mAuthenticator;

    public FactoryCredentialsProvider(FactoryCredentialsAuthenticator authenticator) {
        mAuthenticator = authenticator;
    }

    public CredentialsProvider newProvider(EmailPasswordLogin login,
                                           Authenticator.Callback callback) {
        return new CredentialsProviderEmailPassword(login, mAuthenticator.newEmailAuthenticator(callback));
    }

    public CredentialsProvider newProvider(FacebookLogin login,
                                           Authenticator.Callback callback) {
        return new CredentialsProviderFacebook(login, mAuthenticator.newFacebookAuthenticator(callback));
    }

    public CredentialsProvider newProvider(GoogleLogin login,
                                           Authenticator.Callback callback) {
        return new CredentialsProviderGoogle(login, mAuthenticator.newGoogleAuthenticator(callback));
    }

    public CredentialsProvider newProvider(TwitterLogin login,
                                           Authenticator.Callback callback) {
        return new CredentialsProviderTwitter(login, mAuthenticator.newTwitterAuthenticator(callback));
    }
}
