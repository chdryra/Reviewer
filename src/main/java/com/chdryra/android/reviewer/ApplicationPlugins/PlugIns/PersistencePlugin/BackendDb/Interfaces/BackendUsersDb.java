/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Interfaces;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .Implementation.AuthorProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb
        .Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation.User;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface BackendUsersDb {
    interface AddProfileCallback {
        void onProfileAdded(User user, @Nullable BackendError error);
    }

    interface GetProfileCallback {
        void onProfile(AuthorProfile profile, @Nullable BackendError error);
    }

    interface UpdateProfileCallback {
        void onProfileUpdated(User user, @Nullable BackendError error);
    }

    void createUser(EmailPassword emailPassword, AuthorProfile profile, AddProfileCallback callback);

    void addProfile(User user, AddProfileCallback callback);

    void getProfile(String userId, GetProfileCallback callback);

    void updateProfile(User user, UpdateProfileCallback callback);

    void registerObserver(DbObserver<User> observer);

    void unregisterObserver(DbObserver<User> observer);
}
