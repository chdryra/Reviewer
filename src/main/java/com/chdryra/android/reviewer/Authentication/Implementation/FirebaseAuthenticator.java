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
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseAuthenticator implements Authenticator {
    private static final String NAME = "Firebase";
    private static final String FACEBOOK = "facebook";
    private static final String TWITTER = "twitter";
    private static final String GOOGLE = "google";
    private static final String EMAIL = "email";

    private Firebase mRoot;

    public FirebaseAuthenticator(Firebase root) {
        mRoot = root;
    }

    @Override
    public void authenticateEmailPasswordCredentials(String email, String password, final AuthenticatorCallback callback) {
        mRoot.authWithPassword(email, password, new Firebase.AuthResultHandler() {

            @Override
            public void onAuthenticated(AuthData authData) {
                callback.onSuccess(EMAIL);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onFailure(EMAIL, getError(firebaseError));
            }
        });
    }

    @Override
    public void authenticateFacebookCredentials(String token, final AuthenticatorCallback callback) {
        mRoot.authWithOAuthToken(FACEBOOK, token, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                callback.onSuccess(FACEBOOK);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onFailure(FACEBOOK, getError(firebaseError));
            }
        });
    }

    @Override
    public void authenticateTwitterCredentials(String token, String secret, long userId, final AuthenticatorCallback callback) {
        Map<String, String> options = new HashMap<>();
        options.put("oauth_token", token);
        options.put("oauth_token_secret", secret);
        options.put("user_id", String.valueOf(userId));
        mRoot.authWithOAuthToken(TWITTER, options, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                callback.onSuccess(TWITTER);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onFailure(TWITTER, getError(firebaseError));
            }
        });
    }

    @Override
    public void authenticateGoogleCredentials(final String token, final AuthenticatorCallback callback) {
        mRoot.authWithOAuthToken(GOOGLE, token, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                callback.onSuccess(GOOGLE);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                callback.onFailure(GOOGLE, getError(firebaseError));
            }
        });
    }

    private AuthenticationError getError(FirebaseError error) {
        if(error.getCode() == FirebaseError.EMAIL_TAKEN) {
            return new AuthenticationError(NAME, AuthenticationError.Reason.EMAIL_TAKEN);
        } else if(error.getCode() == FirebaseError.INVALID_EMAIL){
            return new AuthenticationError(NAME, AuthenticationError.Reason.INVALID_EMAIL);
        } else if(error.getCode() == FirebaseError.INVALID_PASSWORD){
            return new AuthenticationError(NAME, AuthenticationError.Reason.INVALID_PASSWORD);
        } else if(error.getCode() == FirebaseError.USER_DOES_NOT_EXIST){
            return new AuthenticationError(NAME, AuthenticationError.Reason.UNKNOWN_USER);
        } else if(error.getCode() == FirebaseError.INVALID_CREDENTIALS){
            return new AuthenticationError(NAME, AuthenticationError.Reason.INVALID_CREDENTIALS);
        } else if(error.getCode() == FirebaseError.DISCONNECTED){
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else if(error.getCode() == FirebaseError.MAX_RETRIES){
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else if(error.getCode() == FirebaseError.NETWORK_ERROR){
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else if(error.getCode() == FirebaseError.UNAVAILABLE){
            return new AuthenticationError(NAME, AuthenticationError.Reason.NETWORK_ERROR);
        } else {
            return new AuthenticationError(NAME, AuthenticationError.Reason.PROVIDER_ERROR, error.getDetails());
        }
    }
}
