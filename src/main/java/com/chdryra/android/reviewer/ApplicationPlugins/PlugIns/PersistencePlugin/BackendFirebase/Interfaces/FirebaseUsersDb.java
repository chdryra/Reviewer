/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Interfaces;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Implementation.User;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase
        .Implementation.UserProfile;
import com.firebase.client.FirebaseError;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FirebaseUsersDb {

    interface AddUserCallback {
        void onUserAdded(User user, @Nullable FirebaseError error);

    }

    interface GetProfileCallback {
        void onUserProfile(UserProfile profile, @Nullable FirebaseError error);
    }

    interface UpdateProfileCallback {
        void onProfileUpdated(UserProfile userProfile, @Nullable FirebaseError error);
    }

    void addUser(User user, AddUserCallback callback);

    void getUser(String fbUserId, GetProfileCallback callback);

    void updateProfile(UserProfile userProfile, UpdateProfileCallback callback);

    void registerObserver(FirebaseDbObserver<UserProfile> observer);

    void unregisterObserver(FirebaseDbObserver<UserProfile> observer);
}
