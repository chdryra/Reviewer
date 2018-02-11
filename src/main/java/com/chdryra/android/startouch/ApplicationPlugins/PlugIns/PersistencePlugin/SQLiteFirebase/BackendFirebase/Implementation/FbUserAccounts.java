/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .BackendFirebase.Implementation;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.UserProfileConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FactoryFbProfile;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FactoryUserAccount;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FbDataReferencer;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.startouch.Authentication.Factories.FactoryAuthorProfileSnapshot;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfile;
import com.chdryra.android.startouch.Authentication.Interfaces.AuthorProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccount;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Utils.EmailAddress;
import com.chdryra.android.startouch.Utils.EmailPassword;
import com.chdryra.android.startouch.Utils.Password;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FbUserAccounts implements UserAccounts {
    private static final DbUpdater.UpdateType INSERT_OR_UPDATE
            = DbUpdater.UpdateType.INSERT_OR_UPDATE;
    private static final AuthenticationError NAME_TAKEN_ERROR =
            new AuthenticationError(ApplicationInstance.APP_NAME, AuthenticationError.Reason
                    .NAME_TAKEN);
    private static final AuthenticationError UNKNOWN_USER_ERROR = new AuthenticationError
            (FirebaseBackend.NAME, AuthenticationError.Reason.UNKNOWN_USER);
    private static final AuthenticationError UNKNOWN_AUTHOR_ERROR = new AuthenticationError
            (FirebaseBackend.NAME, AuthenticationError.Reason.UNKNOWN_AUTHOR);
    private static final AuthenticationError MISMATCH_ERROR = new AuthenticationError(ApplicationInstance
            .APP_NAME, AuthenticationError.Reason
            .AUTHORISATION_REFUSED, "Account and profile author do not match");

    private final Firebase mDataRoot;
    private final FbUsersStructure mStructure;
    private final FbDataReferencer mReferencer;
    private final UserProfileConverter mConverter;
    private final FactoryFbProfile mProfileFactory;
    private final FactoryAuthorProfileSnapshot mProfileSnapshotFactory;
    private final AuthorsRepo mAuthorsRepo;
    private final FactoryUserAccount mAccountFactory;

    private interface CheckNameExistsCallback {
        void onNameDoesNotExist();

        void onNameExists();

        void onError(CallbackMessage message);
    }

    public FbUserAccounts(Firebase dataRoot,
                          FbUsersStructure structure,
                          FbDataReferencer referencer,
                          UserProfileConverter converter,
                          FactoryFbProfile profileFactory,
                          FactoryAuthorProfileSnapshot profileSnapshotFactory,
                          AuthorsRepo authorsRepo,
                          FactoryUserAccount accountFactory) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mReferencer = referencer;
        mConverter = converter;
        mProfileFactory = profileFactory;
        mProfileSnapshotFactory = profileSnapshotFactory;
        mAuthorsRepo = authorsRepo;
        mAccountFactory = accountFactory;
    }

    @Override
    public void createUser(EmailPassword emailPassword, UserAccounts.CreateUserCallback callback) {
        EmailAddress email = emailPassword.getEmail();
        Password password = emailPassword.getPassword();
        mDataRoot.createUser(email.toString(), password.toString(), createUserCallback(callback));
    }

    @Override
    public AuthorProfile newProfile(String name, @Nullable Bitmap photo) {
        return mProfileSnapshotFactory.newProfile(name, photo);
    }

    @Override
    public AuthorProfile newProfile(AuthorProfile oldProfile, @Nullable
            String name, @Nullable Bitmap photo) {
        return mProfileSnapshotFactory.newUpdatedProfile(oldProfile, name, photo);
    }

    @Override
    public void createAccount(final AuthenticatedUser authUser,
                              final AuthorProfile profile,
                              final CreateAccountCallback callback) {
        checkNameExists(profile.getAuthor().getName(), new CheckNameExistsCallback() {
            @Override
            public void onNameDoesNotExist() {
                createProfile(authUser, profile, callback);
            }

            @Override
            public void onNameExists() {
                callback.onAccountCreated(getUserAccount(authUser), profile, NAME_TAKEN_ERROR);
            }

            @Override
            public void onError(CallbackMessage message) {
                callback.onAccountCreated(getUserAccount(authUser), profile, newError(message));
            }
        });
    }

    @Override
    public void updateProfile(final UserAccount account,
                              final AuthorProfile oldProfile,
                              final AuthorProfile newProfile,
                              final UpdateProfileCallback callback) {
        final AuthenticatedUser user = account.getAccountHolder();
        if (user.getAuthorId() == null ||
                !user.getAuthorId().toString().equals(newProfile.getAuthor().getAuthorId()
                        .toString())) {
            callback.onAccountUpdated(newProfile, MISMATCH_ERROR);
            return;
        }


        String oldName = oldProfile.getAuthor().getName();
        String newName = newProfile.getAuthor().getName();
        if (oldName.equals(newName)) {
            updateProfile(user, oldProfile, newProfile, callback);
        } else {
            checkNameExists(newName, new CheckNameExistsCallback() {
                @Override
                public void onNameDoesNotExist() {
                    updateProfile(user, oldProfile, newProfile, callback);
                }

                @Override
                public void onNameExists() {
                    callback.onAccountUpdated(newProfile, NAME_TAKEN_ERROR);
                }

                @Override
                public void onError(CallbackMessage message) {
                    callback.onAccountUpdated(newProfile, newError(message));
                }
            });
        }
    }

    @Override
    public void getAccount(final AuthenticatedUser authUser, final GetAccountCallback callback) {
        AuthorId authorId = authUser.getAuthorId();
        if (authorId != null) {
            getAccountPrivate(authUser, callback);
        } else {
            Firebase db = mStructure.getUserAuthorMappingDb(mDataRoot, authUser.getProvidersId());
            doSingleEvent(db, getAuthorIdThenAccount(authUser, callback));
        }
    }

    @NonNull
    private AuthenticationError newError(CallbackMessage message) {
        return new AuthenticationError(FirebaseBackend.NAME,
                AuthenticationError.Reason.PROVIDER_ERROR, message.getMessage());
    }

    private void checkNameExists(String name, final CheckNameExistsCallback callback) {
        mAuthorsRepo.getAuthorId(name,
                new AuthorsRepo.AuthorIdCallback() {
                    @Override
                    public void onAuthorId(DataReference<AuthorId> authorId,
                                           CallbackMessage message) {
                        if (message.isOk()) {
                            callback.onNameExists();
                        } else if (AuthorsRepo.Error.NAME_NOT_FOUND.name().equals(message
                                .getMessage())) {
                            callback.onNameDoesNotExist();
                        } else {
                            callback.onError(message);
                        }
                    }
                });
    }

    @NonNull
    private ValueEventListener getAuthorIdThenAccount(final AuthenticatedUser authUser, final
    GetAccountCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String authorId = (String) dataSnapshot.getValue();
                if (authorId == null) {
                    notifyAccountError(authUser, UNKNOWN_USER_ERROR, callback);
                } else {
                    authUser.setAuthorId(authorId);
                    getAccountPrivate(authUser, callback);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                notifyAccountError(authUser, newError(firebaseError), callback);
            }
        };
    }

    private void notifyAccountError(AuthenticatedUser authUser, AuthenticationError error,
                                    GetAccountCallback callback) {
        callback.onAccount(mAccountFactory.newNullAccount(authUser), error);
    }

    private void getAccountPrivate(AuthenticatedUser authUser,
                                   GetAccountCallback callback) {
        AuthorId authorId = authUser.getAuthorId();
        if (authorId == null) {
            notifyAccountError(authUser, UNKNOWN_AUTHOR_ERROR, callback);
            return;
        }

        callback.onAccount(getUserAccount(authUser), null);
    }

    @NonNull
    private UserAccount getUserAccount(@Nullable AuthenticatedUser user) {
        if (user == null || user.getAuthorId() == null) {
            return user == null ? mAccountFactory.newNullAccount() : mAccountFactory
                    .newNullAccount(user);
        } else {
            AuthorId authorId = user.getAuthorId();
            return mAccountFactory.newAccount(user, getAuthorProfile(authorId), getSocialProfile(authorId));
        }
    }

    private AuthorProfileRef getAuthorProfile(AuthorId authorId) {
        return mProfileFactory.newAuthorProfile(authorId);
    }

    private SocialProfileRef getSocialProfile(AuthorId authorId) {
        return mProfileFactory.newSocialProfile(authorId);
    }

    private void createProfile(final AuthenticatedUser authUser,
                               final AuthorProfile profile,
                               final CreateAccountCallback callback) {
        final User user = mConverter.toUser(authUser, profile);
        DbUpdater<User> usersUpdater = mStructure.getUsersUpdater();
        Map<String, Object> map = usersUpdater.getUpdatesMap(user, INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                authUser.setAuthorId(profile.getAuthor().getAuthorId().toString());
                callback.onAccountCreated(getUserAccount(authUser), profile, newError
                        (firebaseError));
            }
        });
    }

    private void updateProfile(final AuthenticatedUser authUser,
                               final AuthorProfile oldProfile,
                               final AuthorProfile newProfile,
                               final UpdateProfileCallback callback) {
        final User user = mConverter.toUser(authUser, oldProfile, newProfile);
        DbUpdater<User> profileUpdater = mStructure.getProfileUpdater();
        Map<String, Object> map = profileUpdater.getUpdatesMap(user, INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                callback.onAccountUpdated(newProfile, newError(firebaseError));
            }
        });
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }

    @NonNull
    private Firebase.ValueResultHandler<Map<String, Object>>
    createUserCallback(final CreateUserCallback callback) {
        return new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                callback.onUserCreated(mConverter.newAuthenticatedUser(FirebaseBackend.NAME,
                        (String) result.get("uid")), null);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                callback.onUserCreated(mConverter.newNullAuthenticatedUser(), newError
                        (firebaseError));
            }
        };
    }

    @Nullable
    private AuthenticationError newError(@Nullable FirebaseError firebaseError) {
        return firebaseError == null ? null : FirebaseBackend.authenticationError(firebaseError);
    }

}
