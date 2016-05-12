/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.facebook.AccessToken;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseAuthenticator implements UserAuthenticator {
    private static final String FACEBOOK = "facebook";
    private static final String TWITTER = "twitter";
    private static final String GOOGLE = "google";
    private static final String EMAIL = "email";

    private Firebase mRoot;

    public FirebaseAuthenticator(Firebase root) {
        mRoot = root;
    }

    @Override
    public void authenticateUser(EmailPassword emailPassword, final
    AuthenticatorCallback callback) {
        String email = emailPassword.getEmail().toString();
        String password = emailPassword.getPassword().toString();
        doEmailAuthentication(callback, email, password, EMAIL);
    }

    @Override
    public void authenticateUser(AccessToken token, final AuthenticatorCallback
            callback) {
        final String facebook = FACEBOOK;
        mRoot.authWithOAuthToken(facebook, token.getToken(), getResultHandler(callback, facebook));
    }

    @NonNull
    private Firebase.AuthResultHandler getResultHandler(final AuthenticatorCallback callback, final
    String provider) {
        return new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                callback.onAuthenticated(new AuthenticatedUser(provider, authData.getUid()));
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onAuthenticationError(FirebaseBackend.authenticationError(firebaseError));
            }
        };
    }

    @Override
    public void authenticateUser(TwitterSession session, final
    AuthenticatorCallback callback) {
        TwitterAuthToken authToken = session.getAuthToken();
        Map<String, String> options = new HashMap<>();
        options.put("oauth_token", authToken.token);
        options.put("oauth_token_secret", authToken.secret);
        options.put("user_id", String.valueOf(session.getUserId()));
        mRoot.authWithOAuthToken(TWITTER, options, getResultHandler(callback, TWITTER));
    }

    @Override
    public void authenticateUser(GoogleSignInAccount account, AuthenticatorCallback
            callback) {
        String email = account.getEmail();
        String id = account.getId();
        if (email == null || id == null) {
            callback.onAuthenticationError(new AuthenticationError(GOOGLE, AuthenticationError.Reason
                    .INVALID_CREDENTIALS));
        } else {
            doEmailAuthentication(callback, email, id, GOOGLE);
        }
    }

    private void doEmailAuthentication(final AuthenticatorCallback callback, String email, String
            password, final String provider) {
        mRoot.authWithPassword(email, password, getResultHandler(callback, provider));
    }
}
