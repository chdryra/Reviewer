/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses
        .AuthorProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses
        .BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .FirebaseStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses
        .DbObserver;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses
        .BackendUsersDb;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.Password;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
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
    private ArrayList<DbObserver<User>> mObservers;

    public FirebaseUsersDbImpl(Firebase dataRoot,
                               FirebaseStructure structure) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mObservers = new ArrayList<>();
    }

    @Override
    public void createUser(EmailPassword emailPassword, final AuthorProfile profile, final
    AddProfileCallback callback) {
        EmailAddress email = emailPassword.getEmail();
        Password password = emailPassword.getPassword();
        mDataRoot.createUser(email.toString(), password.toString(),
                newCreateUserAndAddProfileListener(profile, callback));
    }

    @Override
    public void addProfile(final User user, final AddProfileCallback callback) {
        DbUpdater<User> usersUpdater = mStructure.getUsersUpdater();
        Map<String, Object> map = usersUpdater.getUpdatesMap(user, INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        callback.onProfileAdded(user, newBackendError(firebaseError));
                    }
                }
        );
    }

    @Override
    public void getProfile(String userId, GetProfileCallback callback) {
        Firebase userMappingRoot = mDataRoot.child(mStructure.pathToUserAuthorMapping(userId));
        doSingleEvent(userMappingRoot, newGetProfileForUserListener(callback));
    }

    @Override
    public void updateProfile(final User user, final UpdateProfileCallback callback) {
        Map<String, Object> map = mStructure.getProfileUpdater().getUpdatesMap(user,
                INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                callback.onProfileUpdated(user, newBackendError(firebaseError));
            }
        });
    }

    @Override
    public void registerObserver(DbObserver<User> observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(DbObserver<User> observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }

    @NonNull
    private Firebase.ValueResultHandler<Map<String, Object>>
    newCreateUserAndAddProfileListener(final AuthorProfile profile, final AddProfileCallback
            callback) {
        return new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                User user = new User((String) result.get("uid"), profile);
                addProfile(user, callback);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                callback.onProfileAdded(new User(), newBackendError(firebaseError));
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
                listener.onProfile(dataSnapshot.getValue(AuthorProfile.class), null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                notifyNoProfile(listener, firebaseError);
            }
        };
    }

    private void notifyNoProfile(GetProfileCallback listener, FirebaseError firebaseError) {
        listener.onProfile(new AuthorProfile(), newBackendError(firebaseError));
    }

    private BackendError newBackendError(FirebaseError firebaseError) {
        return FirebaseBackend.backendError(firebaseError);
    }
}
