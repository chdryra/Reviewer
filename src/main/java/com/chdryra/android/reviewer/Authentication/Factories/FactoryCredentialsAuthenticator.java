/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Authentication.Implementation.CredentialsAuthenticator;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryCredentialsAuthenticator {
    private final UserAuthenticator mUserAuthenticator;

    public FactoryCredentialsAuthenticator(UserAuthenticator userAuthenticator) {
        mUserAuthenticator = userAuthenticator;
    }

    public CredentialsAuthenticator<EmailPassword> newEmailAuthenticator(Authenticator.Callback callback) {
        return new CredentialsAuthenticator<>(new Authenticator<EmailPassword>() {
            @Override
            public void authenticate(EmailPassword credentials, Callback callback) {
                mUserAuthenticator.authenticateUser(credentials, callback);
            }
        }, callback);
    }

    public CredentialsAuthenticator<AccessToken> newFacebookAuthenticator(Authenticator.Callback callback) {
        return new CredentialsAuthenticator<>(new Authenticator<AccessToken>() {
            @Override
            public void authenticate(AccessToken credentials, Callback callback) {
                mUserAuthenticator.authenticateUser(credentials, callback);
            }
        }, callback);
    }
    
    public CredentialsAuthenticator<GoogleSignInAccount> newGoogleAuthenticator(Authenticator.Callback callback) {
        return new CredentialsAuthenticator<>(new Authenticator<GoogleSignInAccount>() {
            @Override
            public void authenticate(GoogleSignInAccount credentials, Callback callback) {
                mUserAuthenticator.authenticateUser(credentials, callback);
            }
        }, callback);
    }

    public CredentialsAuthenticator<TwitterSession> newTwitterAuthenticator(Authenticator.Callback callback) {
        return new CredentialsAuthenticator<>(new Authenticator<TwitterSession>() {
            @Override
            public void authenticate(TwitterSession credentials, Callback callback) {
                mUserAuthenticator.authenticateUser(credentials, callback);
            }
        }, callback);
    }
}
