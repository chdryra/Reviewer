/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .SQLiteFirebase.Implementation.BackendFirebase
        .Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.UserProfileConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Interfaces.UsersDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Interfaces.FbUsersStructure;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Structuring.DbUpdater;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DefaultAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
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
public class FirebaseUsersDb implements UsersDb {
    private static final DbUpdater.UpdateType INSERT_OR_UPDATE
            = DbUpdater.UpdateType.INSERT_OR_UPDATE;
    private static final String NAME = FirebaseBackend.NAME;
    private static final AuthenticationError NAME_TAKEN_ERROR =
            new AuthenticationError(ApplicationInstance.APP_NAME, AuthenticationError.Reason
                    .NAME_TAKEN);

    private Firebase mDataRoot;
    private FbUsersStructure mStructure;
    private UserProfileConverter mUserFactory;

    public FirebaseUsersDb(Firebase dataRoot,
                           FbUsersStructure structure,
                           UserProfileConverter userFactory) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mUserFactory = userFactory;
    }

    @Override
    public String getProviderName() {
        return FirebaseBackend.NAME;
    }

    @Override
    public void createUser(EmailPassword emailPassword, CreateUserCallback callback) {
        EmailAddress email = emailPassword.getEmail();
        Password password = emailPassword.getPassword();
        mDataRoot.createUser(email.toString(), password.toString(), createUserCallback(callback));
    }

    @Override
    public void addProfile(final User user, final CreateAccountCallback callback) {
        Profile profile = user.getProfile();
        if (profile == null) {
            callback.onProfileAddedError(new AuthenticationError(NAME,
                    AuthenticationError.Reason.AUTHORISATION_REFUSED, "No profile"));
            return;
        }

        String name = profile.getAuthor().getName();
        checkNameConflict(name, new UserConflictCallback() {
            @Override
            public void onUserName(String name, @Nullable AuthenticationError error) {
                if (error == null) {
                    addNewProfile(user, callback);
                } else {
                    callback.onProfileAddedError(error);
                }
            }
        });
    }

    private void checkNameConflict(final String authorName, final UserConflictCallback callback) {
        Firebase db = mStructure.getNameAuthorMappingDb(mDataRoot, authorName);
        doSingleEvent(db, checkNameDoesNotExist(authorName, callback));
    }

    @Override
    public void getProfile(User user, GetProfileCallback callback) {
        Firebase db = mStructure.getUserAuthorMappingDb(mDataRoot, user.getProviderUserId());
        doSingleEvent(db, getAuthorIdThenProfile(callback));
    }

    @Override
    public void updateProfile(final User user, final UpdateProfileCallback callback) {
        Map<String, Object> map
                = mStructure.getProfileUpdater().getUpdatesMap(user, INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, updateProfileCallback(user, callback));
    }

    private void addNewProfile(User user, CreateAccountCallback callback) {
        DbUpdater<User> usersUpdater = mStructure.getUsersUpdater();
        Map<String, Object> map = usersUpdater.getUpdatesMap(user, INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, addProfileCallback(user, callback));
    }

    @NonNull
    private ValueEventListener checkNameDoesNotExist(final String authorName, final
    UserConflictCallback callback) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    callback.onUserName(authorName, null);
                } else {
                    callback.onUserName(authorName, NAME_TAKEN_ERROR);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onUserName(authorName, FirebaseBackend.authenticationError(firebaseError));
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener updateProfileCallback(final User user, final
    UpdateProfileCallback callback) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    callback.onProfileUpdated(user);
                } else {
                    callback.onProfileUpdatedError(newError(firebaseError));
                }
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener addProfileCallback(final User user, final
    CreateAccountCallback callback) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    callback.onProfileAdded(user);
                } else {
                    callback.onProfileAddedError(newError(firebaseError));
                }
            }
        };
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
                User user = mUserFactory.newUser(getProviderName(), (String) result.get("uid"));
                callback.onUserCreated(user);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                callback.onUserCreationError(newError(firebaseError));
            }
        };
    }

    @NonNull
    private ValueEventListener getAuthorIdThenProfile(final GetProfileCallback listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                if (value == null) {
                    notifyNoProfile(listener, new FirebaseError(FirebaseError.USER_DOES_NOT_EXIST,
                            "No mapping for user " + dataSnapshot.getKey()));
                } else {
                    AuthorId authorId = new DefaultAuthorId(value.toString());
                    Firebase profile = mStructure.getProfileDb(mDataRoot, authorId);
                    doSingleEvent(profile, newGetProfileForAuthorListener(listener));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                notifyNoProfile(listener, firebaseError);
            }
        };
    }

    @NonNull
    private ValueEventListener newGetProfileForAuthorListener(final GetProfileCallback listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onProfile(dataSnapshot.getValue(Profile.class));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                notifyNoProfile(listener, firebaseError);
            }
        };
    }

    private void notifyNoProfile(GetProfileCallback listener, FirebaseError firebaseError) {
        listener.onProfileError(newError(firebaseError));
    }

    private AuthenticationError newError(FirebaseError firebaseError) {
        return FirebaseBackend.authenticationError(firebaseError);
    }
}
