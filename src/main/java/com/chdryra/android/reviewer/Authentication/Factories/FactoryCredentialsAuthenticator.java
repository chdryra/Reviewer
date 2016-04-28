/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import android.content.Context;

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
    private Context mContext;
    private Authenticator mAuthenticator;

    public FactoryCredentialsAuthenticator(Context context, Authenticator authenticator) {
        mContext = context;
        mAuthenticator = authenticator;
    }

    public EmailAuthenticator newEmailAuthenticator(AuthenticatorCallback callback) {
        return new EmailAuthenticator(mAuthenticator, callback);
    }

    public FacebookAuthenticator newFacebookAuthenticator(AuthenticatorCallback callback) {
        return new FacebookAuthenticator(mAuthenticator, callback);
    }
    
    public GoogleAuthenticator newGoogleAuthenticator(AuthenticatorCallback callback, GoogleAuthenticator.UserRecoverableExceptionHandler handler) {
        return new GoogleAuthenticator(mContext, mAuthenticator, callback, handler);
    }

    public TwitterAuthenticator newTwitterAuthenticator(AuthenticatorCallback callback) {
        return new TwitterAuthenticator(mAuthenticator, callback);
    }
}
