/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .FirebaseStructuring.DbUpdater;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Interfaces.FirebaseDbObserver;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Interfaces.FirebaseUsersDb;
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
public class FirebaseUsersDbImpl implements FirebaseUsersDb {
    private Firebase mDataRoot;
    private FirebaseStructure mStructure;
    private ArrayList<FirebaseDbObserver<User>> mObservers;

    public FirebaseUsersDbImpl(Firebase dataRoot,
                               FirebaseStructure structure) {
        mDataRoot = dataRoot;
        mStructure = structure;
        mObservers = new ArrayList<>();
    }

    @Override
    public void addUser(User user, AddUserCallback callback) {
        Map<String, Object> map = getUserUpdatesMap(user, DbUpdater.UpdateType.INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, newAddListener(user, callback));
    }

    @Override
    public void getProfile(String userId, GetProfileCallback callback) {
        doSingleEvent(getUserMappingRoot(userId), newGetListener(callback));
    }

    @Override
    public void updateProfile(User user, UpdateProfileCallback callback) {
        Map<String, Object> map = getProfileUpdatesMap(user, DbUpdater.UpdateType.INSERT_OR_UPDATE);
        mDataRoot.updateChildren(map, newUpdateListener(user, callback));
    }

    @Override
    public void registerObserver(FirebaseDbObserver<User> observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(FirebaseDbObserver<User> observer) {
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    private Firebase getUserMappingRoot(String userId) {
        return mDataRoot.child(mStructure.pathToUserAuthorMapping(userId));
    }

    @NonNull
    private Map<String, Object> getProfileUpdatesMap(User user, DbUpdater.UpdateType type) {
        return mStructure.getProfileUpdater().getUpdatesMap(user, type);
    }

    @NonNull
    private Map<String, Object> getUserUpdatesMap(User user, DbUpdater.UpdateType type) {
        return mStructure.getUsersUpdater().getUpdatesMap(user, type);
    }

    private void doSingleEvent(Firebase root, ValueEventListener listener) {
        root.addListenerForSingleValueEvent(listener);
    }

    @NonNull
    private ValueEventListener newGetListener(final GetProfileCallback listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String authorId = dataSnapshot.getValue().toString();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onAuthorProfile(new AuthorProfile(), firebaseError);
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newAddListener(final User user,
                                                       final AddUserCallback listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onUserAdded(user, firebaseError);
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newUpdateListener(final User user,
                                                       final UpdateProfileCallback listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onProfileUpdated(user, firebaseError);
            }
        };
    }
}
