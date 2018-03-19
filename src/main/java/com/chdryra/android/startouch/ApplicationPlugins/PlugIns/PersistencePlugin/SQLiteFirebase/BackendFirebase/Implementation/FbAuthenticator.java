/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.UserProfileConverter;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.startouch.Authentication.Interfaces.CredentialsAuthenticator;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.startouch.Utils.EmailAddress;
import com.chdryra.android.startouch.Utils.EmailAddressException;
import com.chdryra.android.startouch.Utils.EmailPassword;
import com.chdryra.android.startouch.Utils.Password;
import com.facebook.AccessToken;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbAuthenticator implements UserAuthenticator, Firebase.AuthStateListener {
    private static final String FACEBOOK = "facebook";
    private static final String TWITTER = "twitter";
    private static final String GOOGLE = "google";
    private static final String EMAIL = "email";

    private final Firebase mRoot;
    private final UserProfileConverter mUsersFactory;
    private final UserAccounts mAccounts;
    private final ArrayList<UserStateObserver> mObservers;
    private AuthenticatedUser mLoggedIn;

    public FbAuthenticator(Firebase root,
                           UserAccounts accounts,
                           UserProfileConverter usersFactory) {
        mRoot = root;
        mUsersFactory = usersFactory;
        mAccounts = accounts;
        mObservers = new ArrayList<>();
        newUser(mRoot.getAuth());
        mRoot.addAuthStateListener(this);
    }

    @Override
    public void registerObserver(UserStateObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(UserStateObserver observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @Override
    public void onAuthStateChanged(AuthData authData) {
        newUser(authData);
    }

    @Override
    public void logout() {
        mRoot.unauth();
    }

    @Nullable
    @Override
    public AuthenticatedUser getAuthenticatedUser() {
        if (mLoggedIn == null) newUser(mRoot.getAuth());

        return mLoggedIn;
    }

    @Override
    public void authenticateUser(EmailPassword emailPassword,
                                 final CredentialsAuthenticator.Callback callback) {
        String email = emailPassword.getEmail().toString();
        String password = emailPassword.getPassword().toString();
        mRoot.authWithPassword(email, password, getResultHandler(callback, EMAIL));
    }

    @Override
    public void authenticateUser(AccessToken token, final CredentialsAuthenticator.Callback
            callback) {
        mRoot.authWithOAuthToken(FACEBOOK, token.getToken(), getResultHandler(callback, FACEBOOK));
    }

    @Override
    public void authenticateUser(TwitterSession session, final CredentialsAuthenticator.Callback
            callback) {
        TwitterAuthToken authToken = session.getAuthToken();
        Map<String, String> options = new HashMap<>();
        options.put("oauth_token", authToken.token);
        options.put("oauth_token_secret", authToken.secret);
        options.put("user_id", String.valueOf(session.getUserId()));
        mRoot.authWithOAuthToken(TWITTER, options, getResultHandler(callback, TWITTER));
    }

    @Override
    public void authenticateUser(final GoogleSignInAccount account,
                                 final CredentialsAuthenticator.Callback callback) {
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
                        createGoogleUser(account, callback);
                    } else {
                        notifyNotAuthenticated(error, callback);
                    }
                }
            });
        }
    }

    private void notifyObservers(AuthenticatedUser old) {
        for (UserStateObserver observer : mObservers) {
            observer.onUserStateChanged(old, mLoggedIn);
        }
    }

    private void newUser(AuthData auth) {
        AuthenticatedUser user = auth != null ?
                mUsersFactory.newAuthenticatedUser(auth.getProvider(), auth.getUid()) : null;

        AuthenticatedUser old = mLoggedIn;
        mLoggedIn = user;
        notifyObservers(old);
    }

    @NonNull
    private Firebase.AuthResultHandler getResultHandler(final CredentialsAuthenticator.Callback
                                                                callback, final
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

    private void notifyNotAuthenticated(AuthenticationError error, CredentialsAuthenticator
            .Callback callback) {
        callback.onAuthenticationError(error);
    }

    private void notifyOnAuthenticated(AuthData authData, String provider,
                                       CredentialsAuthenticator.Callback
                                               callback) {
        AuthenticatedUser user
                = mUsersFactory.newAuthenticatedUser(provider, authData.getUid());
        callback.onAuthenticated(user);
    }

    private void createGoogleUser(final GoogleSignInAccount account,
                                  final CredentialsAuthenticator.Callback callback) {
        final String email = account.getEmail();
        final String password = account.getId();

        AuthenticationError emailError
                = new AuthenticationError(GOOGLE, AuthenticationError.Reason.INVALID_EMAIL);
        AuthenticationError pwError
                = new AuthenticationError(GOOGLE, AuthenticationError.Reason.INVALID_CREDENTIALS);

        if (email == null) {
            notifyNotAuthenticated(emailError, callback);
            return;
        }

        if (password == null) {
            notifyNotAuthenticated(pwError, callback);
            return;
        }

        EmailAddress emailAddress;
        try {
            emailAddress = new EmailAddress(email);
        } catch (EmailAddressException e) {
            notifyNotAuthenticated(emailError, callback);
            return;
        }

        EmailPassword ep = new EmailPassword(emailAddress, new Password(password));
        mAccounts.createUser(ep, new UserAccounts.CreateUserCallback() {
            @Override
            public void onUserCreated(AuthenticatedUser user, @Nullable AuthenticationError error) {
                if (error == null) {
                    authenticateUser(account, callback);
                } else {
                    notifyNotAuthenticated(error, callback);
                }
            }
        });
    }
}
