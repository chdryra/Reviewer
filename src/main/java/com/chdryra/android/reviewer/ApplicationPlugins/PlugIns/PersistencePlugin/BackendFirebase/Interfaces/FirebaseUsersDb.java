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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.AuthorProfile;
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
        void onAuthorProfile(AuthorProfile profile, @Nullable FirebaseError error);
    }

    interface UpdateProfileCallback {
        void onProfileUpdated(User user, @Nullable FirebaseError error);
    }

    void addUser(User user, AddUserCallback callback);

    void getProfile(String userId, GetProfileCallback callback);

    void updateProfile(User user, UpdateProfileCallback callback);

    void registerObserver(FirebaseDbObserver<User> observer);

    void unregisterObserver(FirebaseDbObserver<User> observer);
}
