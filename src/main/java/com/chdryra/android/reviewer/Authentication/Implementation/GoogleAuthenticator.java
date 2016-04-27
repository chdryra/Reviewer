/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Authentication.Interfaces.Authenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleCredentialsCallback;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.IOException;

/**
 * Created by: Rizwan Choudrey
 * On: 27/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleAuthenticator implements GoogleCredentialsCallback {
    private final Context mContext;
    private final Authenticator mAuthenticator;
    private final AuthenticatorCallback mCallback;

    public GoogleAuthenticator(Context context, Authenticator authenticator,
                               AuthenticatorCallback callback) {
        mContext = context;
        mAuthenticator = authenticator;
        mCallback = callback;
    }

    @Override
    public void onCredentialsObtained(String provider, GoogleSignInAccount credentials) {
        new TokenGetter(provider, credentials).execute();
    }

    @Override
    public void onCredentialsFailure(String provider, AuthenticationError error) {
        mCallback.onFailure(provider, error);
    }

    private void doAuthentication(String provider, @Nullable String idToken) {
        if (idToken != null) {
            mAuthenticator.authenticateGoogleCredentials(idToken, mCallback);
        } else {
            onCredentialsFailure(provider, new AuthenticationError(provider,
                    AuthenticationError.Reason.INVALID_CREDENTIALS));
        }
    }

    private class TokenGetter extends AsyncTask<Void, Void, String> {
        private String mProvider;
        private GoogleSignInAccount mAccount;

        public TokenGetter(String provider, GoogleSignInAccount account) {
            mProvider = provider;
            mAccount = account;
        }

        @Override
        @Nullable
        protected String doInBackground(Void... params) {
            String scopes = "oauth2:profile email";
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(mContext, mAccount.getEmail(), scopes);
            } catch (IOException | GoogleAuthException e) {
                e.printStackTrace();
            }

            return token;
        }

        @Override
        protected void onPostExecute(String token) {
            doAuthentication(mProvider, token);
        }
    }
}
