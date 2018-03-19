/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Factories;

import com.chdryra.android.startouch.Authentication.Implementation.CredentialsHandler;
import com.chdryra.android.startouch.Authentication.Interfaces.CredentialsAuthenticator;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.startouch.Utils.EmailPassword;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryCredentialsHandler {
    private final UserAuthenticator mUserAuthenticator;

    public FactoryCredentialsHandler(UserAuthenticator userAuthenticator) {
        mUserAuthenticator = userAuthenticator;
    }

    public CredentialsHandler<EmailPassword> newEmailPasswordHandler(CredentialsAuthenticator
                                                                             .Callback callback) {
        return new CredentialsHandler<>(new CredentialsAuthenticator<EmailPassword>() {
            @Override
            public void authenticate(EmailPassword credentials, Callback callback) {
                mUserAuthenticator.authenticateUser(credentials, callback);
            }
        }, callback);
    }

    public CredentialsHandler<AccessToken> newFacebookHandler(CredentialsAuthenticator.Callback
                                                                      callback) {
        return new CredentialsHandler<>(new CredentialsAuthenticator<AccessToken>() {
            @Override
            public void authenticate(AccessToken credentials, Callback callback) {
                mUserAuthenticator.authenticateUser(credentials, callback);
            }
        }, callback);
    }

    public CredentialsHandler<GoogleSignInAccount> newGoogleHandler(CredentialsAuthenticator
                                                                            .Callback callback) {
        return new CredentialsHandler<>(new CredentialsAuthenticator<GoogleSignInAccount>() {
            @Override
            public void authenticate(GoogleSignInAccount credentials, Callback callback) {
                mUserAuthenticator.authenticateUser(credentials, callback);
            }
        }, callback);
    }

    public CredentialsHandler<TwitterSession> newTwitterHandler(CredentialsAuthenticator.Callback
                                                                        callback) {
        return new CredentialsHandler<>(new CredentialsAuthenticator<TwitterSession>() {
            @Override
            public void authenticate(TwitterSession credentials, Callback callback) {
                mUserAuthenticator.authenticateUser(credentials, callback);
            }
        }, callback);
    }
}
