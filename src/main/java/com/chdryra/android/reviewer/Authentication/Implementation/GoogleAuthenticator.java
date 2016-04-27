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
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleCredentialsCallback;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleAuthenticator implements GoogleCredentialsCallback {
    private final Authenticator mAuthenticator;
    private final AuthenticatorCallback mCallback;

    public GoogleAuthenticator(Authenticator authenticator, AuthenticatorCallback callback) {
        mAuthenticator = authenticator;
        mCallback = callback;
    }

    @Override
    public void onCredentialsObtained(String provider, GoogleSignInAccount credentials) {
        String idToken = credentials.getIdToken();
        if (idToken != null) {
            mAuthenticator.authenticateGoogleCredentials(idToken, mCallback);
        } else {
            onCredentialsFailure(provider, new AuthenticationError(provider,
                    AuthenticationError.Reason.INVALID_CREDENTIALS));
        }
    }

    @Override
    public void onCredentialsFailure(String provider, AuthenticationError error) {
        mCallback.onFailure(provider, error);
    }
}
