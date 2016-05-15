/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.BackendFirebase.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.UserProfileTranslator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Interfaces.BackendUsersDb;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailAddressException;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.Utils.Password;
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
    private UserProfileTranslator mUsersFactory;
    private FirebaseUsersDb mDb;

    public FirebaseAuthenticator(Firebase root, FirebaseUsersDb db, UserProfileTranslator usersFactory) {
        mRoot = root;
        mUsersFactory = usersFactory;
        mDb = db;
    }

    @Override
    public void authenticateUser(EmailPassword emailPassword, final
    AuthenticatorCallback callback) {
        String email = emailPassword.getEmail().toString();
        String password = emailPassword.getPassword().toString();
        mRoot.authWithPassword(email, password, getResultHandler(callback, EMAIL));
    }

    @Override
    public void authenticateUser(AccessToken token, final AuthenticatorCallback
            callback) {
        final String facebook = FACEBOOK;
        mRoot.authWithOAuthToken(facebook, token.getToken(), getResultHandler(callback, facebook));
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
    public void authenticateUser(GoogleSignInAccount account, final AuthenticatorCallback
            callback) {
        final String email = account.getEmail();
        final String id = account.getId();
        if (email == null || id == null) {
            AuthenticationError error
                    = new AuthenticationError(GOOGLE, AuthenticationError.Reason
                    .INVALID_CREDENTIALS);
            notifyNotAuthenticated(error, callback);
        } else {
            mRoot.authWithPassword(email, id, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    notifyOnAuthenticated(authData, GOOGLE, callback);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    AuthenticationError error = FirebaseBackend.authenticationError(firebaseError);
                    if (error.is(AuthenticationError.Reason.UNKNOWN_USER)) {
                        createAuthenticatedGoogleUser(email, id, callback);
                    } else {
                        notifyNotAuthenticated(error, callback);
                    }
                }
            });
        }
    }

    @NonNull
    private Firebase.AuthResultHandler getResultHandler(final AuthenticatorCallback callback, final
    String provider) {
        return new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                notifyOnAuthenticated(authData, provider, callback);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                AuthenticationError error = FirebaseBackend.authenticationError(firebaseError);
                notifyNotAuthenticated(error, callback);
            }
        };
    }

    private void notifyNotAuthenticated(AuthenticationError error, AuthenticatorCallback callback) {
        callback.onAuthenticationError(error);
    }

    private void notifyOnAuthenticated(AuthData authData, String provider, AuthenticatorCallback
            callback) {
        AuthenticatedUser user
                = mUsersFactory.newAuthenticatedUser(provider, authData.getUid());
        callback.onAuthenticated(user);
    }

    private void notifyOnAuthenticated(User user, AuthenticatorCallback callback) {
        AuthenticatedUser authUser
                = mUsersFactory.toAuthenticatedUser(user);
        callback.onAuthenticated(authUser);
    }

    private void createAuthenticatedGoogleUser(String email, String password, final
    AuthenticatorCallback callback) {
        EmailAddress emailAddress;
        try {
            emailAddress = new EmailAddress(email);
        } catch (EmailAddressException e) {
            e.printStackTrace();
            notifyNotAuthenticated(new AuthenticationError(GOOGLE, AuthenticationError.Reason
                    .INVALID_EMAIL), callback);
            return;
        }

        EmailPassword ep = new EmailPassword(emailAddress, new Password(password));
        mDb.createUser(ep, new BackendUsersDb.CreateUserCallback() {
            @Override
            public void onUserCreated(User user) {
                notifyOnAuthenticated(user, callback);
            }

            @Override
            public void onUserCreationError(AuthenticationError error) {
                notifyNotAuthenticated(error, callback);
            }
        });
    }
}
