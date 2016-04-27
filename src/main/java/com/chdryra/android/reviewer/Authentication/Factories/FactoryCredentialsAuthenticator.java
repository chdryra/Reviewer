/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Implementation.EmailAuthenticator;
import com.chdryra.android.reviewer.Authentication.Implementation.FacebookAuthenticator;
import com.chdryra.android.reviewer.Authentication.Implementation.GoogleAuthenticator;
import com.chdryra.android.reviewer.Authentication.Implementation.TwitterAuthenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryCredentialsAuthenticator {
    public EmailAuthenticator newEmailAuthentcator(Authenticator authenticator,
                                                   AuthenticatorCallback callback) {
        return new EmailAuthenticator(authenticator, callback);
    }

    public FacebookAuthenticator newFacebookAuthenticator(Authenticator authenticator, 
                                                          AuthenticatorCallback callback) {
        return new FacebookAuthenticator(authenticator, callback);
    }
    
    public GoogleAuthenticator newGoogleAuthenticator(Authenticator authenticator, 
                                                      AuthenticatorCallback callback) {
        return new GoogleAuthenticator(authenticator, callback);
    }

    public TwitterAuthenticator newTwitterAuthenticator(Authenticator authenticator,
                                                        AuthenticatorCallback callback) {
        return new TwitterAuthenticator(authenticator, callback);
    }
}
