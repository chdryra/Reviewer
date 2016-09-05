/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Implementation.CredentialsAuthenticator;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryCredentialsAuthenticator {
    private final UserAuthenticator mAuthenticator;

    public FactoryCredentialsAuthenticator(UserAuthenticator authenticator) {
        mAuthenticator = authenticator;
    }

    public CredentialsAuthenticator<EmailPassword> newEmailAuthenticator(AuthenticatorCallback callback) {
        return new CredentialsAuthenticator<>(callback, new CredentialsAuthenticator.AuthenticationCall<EmailPassword>() {
            @Override
            public void authenticate(EmailPassword credentials, AuthenticatorCallback callback) {
                mAuthenticator.authenticateUser(credentials, callback);
            }
        });
    }

    public CredentialsAuthenticator<AccessToken> newFacebookAuthenticator(AuthenticatorCallback callback) {
        return new CredentialsAuthenticator<>(callback, new CredentialsAuthenticator.AuthenticationCall<AccessToken>() {
            @Override
            public void authenticate(AccessToken credentials, AuthenticatorCallback callback) {
                mAuthenticator.authenticateUser(credentials, callback);
            }
        });
    }
    
    public CredentialsAuthenticator<GoogleSignInAccount> newGoogleAuthenticator(AuthenticatorCallback callback) {
        return new CredentialsAuthenticator<>(callback, new CredentialsAuthenticator.AuthenticationCall<GoogleSignInAccount>() {
            @Override
            public void authenticate(GoogleSignInAccount credentials, AuthenticatorCallback callback) {
                mAuthenticator.authenticateUser(credentials, callback);
            }
        });
    }

    public CredentialsAuthenticator<TwitterSession> newTwitterAuthenticator(AuthenticatorCallback callback) {
        return new CredentialsAuthenticator<>(callback, new CredentialsAuthenticator.AuthenticationCall<TwitterSession>() {
            @Override
            public void authenticate(TwitterSession credentials, AuthenticatorCallback callback) {
                mAuthenticator.authenticateUser(credentials, callback);
            }
        });
    }
}
