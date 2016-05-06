/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .BackendFirebase
        .Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .BackendFirebase.FirebaseStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .Interfaces.BackendUsersDb;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;
import com.chdryra.android.reviewer.Utils.EmailAddress;
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
public class FirebaseUsersDbImpl implements BackendUsersDb {
    private static final DbUpdater.UpdateType INSERT_OR_UPDATE
            = DbUpdater.UpdateType.INSERT_OR_UPDATE;

    private Firebase mDataRoot;
    private FirebaseStructure mStructure;

    public FirebaseUsersDbImpl(Firebase dataRoot,
                               FirebaseStructure structure) {
        mDataRoot = dataRoot;
        mStructure = structure;
    }

    @Override
    public String getProviderName() {
        return FirebaseBackend.NAME;
    }

    @Override
    public void createUser(EmailPassword emailPassword, CreateUserCallback callback) {
        EmailAddress email = emailPassword.getEmail();
        Password password = emailPassword.getPassword();
        mDataRoot.createUser(email.toString(), password.toString(), newCreateUserListener
                (callback));
    }

    @Override
    public void addProfile(final User user, final AddProfileCallback callback) {
        DbUpdater<User> usersUpdater = mStructure.getUsersUpdater();
        Map<String, Object> map = usersUpdater.getUpdatesMap(user, INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, newAddProfileListener(user, callback));
    }

    @Override
    public void getProfile(User user, GetProfileCallback callback) {
        String path = mStructure.pathToUserAuthorMapping(user.getProviderUserId());
        doSingleEvent(mDataRoot.child(path), newGetProfileForUserListener(callback));
    }

    @Override
    public void updateProfile(final User user, final UpdateProfileCallback callback) {
        Map<String, Object> map = mStructure.getProfileUpdater().getUpdatesMap(user,
                INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, newUpdateProfileListener(user, callback));
    }

    @NonNull
    private Firebase.CompletionListener newUpdateProfileListener(final User user, final
    UpdateProfileCallback callback) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    callback.onProfileUpdated(user);
                } else {
                    callback.onProfileUpdatedError(newBackendError(firebaseError));
                }
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newAddProfileListener(final User user, final
    AddProfileCallback callback) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError == null) {
                    callback.onProfileAdded(user);
                } else {
                    callback.onProfileAddedError(newBackendError(firebaseError));
                }
            }
        };
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }

    @NonNull
    private Firebase.ValueResultHandler<Map<String, Object>>
    newCreateUserListener(final CreateUserCallback callback) {
        return new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                callback.onUserCreated(new User(getProviderName(), (String) result.get("uid")));
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                callback.onUserCreationError(newBackendError(firebaseError));
            }
        };
    }

    @NonNull
    private ValueEventListener newGetProfileForUserListener(final GetProfileCallback listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                if (value == null) {
                    notifyNoProfile(listener, new FirebaseError(FirebaseError.USER_DOES_NOT_EXIST,
                            "No mapping for user " + dataSnapshot.getKey()));
                } else {
                    Firebase profile = mDataRoot.child(mStructure.pathToProfile(value.toString()));
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
        listener.onProfileError(newBackendError(firebaseError));
    }

    private BackendError newBackendError(FirebaseError firebaseError) {
        return FirebaseBackend.backendError(firebaseError);
    }
}
