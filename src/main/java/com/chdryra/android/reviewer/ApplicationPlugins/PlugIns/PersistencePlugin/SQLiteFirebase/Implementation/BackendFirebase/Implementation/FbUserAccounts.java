/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.UserProfileConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryAuthorProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryFbReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Factories.FactoryUserAccount;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthorProfileSnapshot;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfileSnapshot;
import com.chdryra.android.reviewer.Authentication.Interfaces.ProfileAuthor;
import com.chdryra.android.reviewer.Authentication.Interfaces.ProfileSocial;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.AuthorReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.DataReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.Utils.Password;
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

    private final Firebase mDataRoot;
    private final FbUsersStructure mStructure;
    private final FactoryFbReference mReferencer;
    private final UserProfileConverter mConverter;
    private final FactoryAuthorProfile mProfileFactory;
    private final FactoryAuthorProfileSnapshot mProfileSnapshotFactory;
    private final AuthorsRepository mAuthorsRepo;
    private final FactoryUserAccount mAccountFactory;

    private interface CheckNameExistsCallback {
        void onNameDoesNotExist();

        void onNameExists();

        void onError(CallbackMessage message);
    }

    public FbUserAccounts(Firebase dataRoot,
                          FbUsersStructure structure,
                          FactoryFbReference referencer,
                          UserProfileConverter converter,
                          FactoryAuthorProfile profileFactory,
                          FactoryAuthorProfileSnapshot profileSnapshotFactory,
                          AuthorsRepository authorsRepo,
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
    public AuthorProfileSnapshot newProfile(String name, @Nullable Bitmap photo) {
        return mProfileSnapshotFactory.newProfile(name, photo);
    }

    @Override
    public AuthorProfileSnapshot newUpdatedProfile(AuthorProfileSnapshot oldProfile, @Nullable
            String name, @Nullable Bitmap photo) {
        return mProfileSnapshotFactory.newUpdatedProfile(oldProfile, name, photo);
    }

    @Override
    public void createAccount(final AuthenticatedUser authUser,
                              final AuthorProfileSnapshot profile,
                              final CreateAccountCallback callback) {
        checkNameExists(profile.getNamedAuthor().getName(), new CheckNameExistsCallback() {
            @Override
            public void onNameDoesNotExist() {
                createProfile(authUser, profile, callback);
            }

            @Override
            public void onNameExists() {
                callback.onAccountCreated(newUserAccount(null, profile), profile, NAME_TAKEN_ERROR);
            }

            @Override
            public void onError(CallbackMessage message) {
                callback.onAccountCreated(newUserAccount(null, profile), profile, newError(message));
            }
        });
    }

    @Override
    public void updateProfile(final UserAccount account,
                              final AuthorProfileSnapshot oldProfile,
                              final AuthorProfileSnapshot newProfile,
                              final UpdateProfileCallback callback) {
        final AuthenticatedUser user = account.getAccountHolder();
        AuthorReference authorUpdate = newProfile.getAuthor();
        if (user.getAuthorId() == null ||
                !user.getAuthorId().toString().equals(authorUpdate.getAuthorId().toString())) {
            callback.onAccountUpdated(newProfile, newError(AuthenticationError.Reason
                    .AUTHORISATION_REFUSED, "Account and profile author do not match"));
            return;
        }


        String oldName = oldProfile.getNamedAuthor().getName();
        String newName = newProfile.getNamedAuthor().getName();
        if(oldName.equals(newName)) {
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
            callback.onAccount(newUserAccount(authUser, new AuthorProfileSnapshot()), null);
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
                new AuthorsRepository.AuthorIdCallback() {
                    @Override
                    public void onAuthorId(DataReference<AuthorId> authorId,
                                           CallbackMessage message) {
                        if (message.isOk()) {
                            callback.onNameExists();
                        } else if (AuthorsRepository.Error.NAME_NOT_FOUND.name().equals(message.getMessage())) {
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
                    callback.onAccount(mAccountFactory.newNullAccount(authUser),
                            UNKNOWN_USER_ERROR);
                } else {
                    authUser.setAuthorId(authorId);
                    ProfileAuthor profile = mProfileFactory.newProfile(authUser.getAuthorId());
                    callback.onAccount(newUserAccount(authUser, profile), null);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onAccount(mAccountFactory.newNullAccount(authUser), newError
                        (firebaseError));
            }
        };
    }

    @NonNull
    private UserAccount newUserAccount(@Nullable AuthenticatedUser user, ProfileAuthor profile) {
        if (user == null) {
            return mAccountFactory.newNullAccount();
        } else {
            ProfileSocial socialProfile
                    = mReferencer.newSocialProfile(user.getAuthorId(), mDataRoot, mStructure);
            return mAccountFactory.newAccount(user, profile, socialProfile);
        }
    }

    private void createProfile(final AuthenticatedUser authUser,
                               final AuthorProfileSnapshot profile,
                               final CreateAccountCallback callback) {
        final User user = mConverter.toUser(authUser, profile);
        DbUpdater<User> usersUpdater = mStructure.getUsersUpdater();
        Map<String, Object> map = usersUpdater.getUpdatesMap(user, INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                authUser.setAuthorId(profile.getNamedAuthor().getAuthorId().toString());
                callback.onAccountCreated(newUserAccount(authUser, profile), profile, newError
                        (firebaseError));
            }
        });
    }

    private void updateProfile(final AuthenticatedUser authUser,
                               final AuthorProfileSnapshot oldProfile,
                               final AuthorProfileSnapshot newProfile,
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

    private AuthenticationError newError(AuthenticationError.Reason reason, String error) {
        return new AuthenticationError(ApplicationInstance.APP_NAME, reason, error);
    }
}
