/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Interfaces;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation.User;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface BackendUsersDb {
    interface CreateUserCallback {
        void onUserCreated(User user);

        void onUserCreationError(BackendError error);
    }

    interface AddProfileCallback {
        void onProfileAdded(User user);

        void onProfileAddedError(BackendError error);
    }

    interface GetProfileCallback {
        void onProfile(Profile profile);

        void onProfileError(BackendError error);
    }

    interface UpdateProfileCallback {
        void onProfileUpdated(User user);

        void onProfileUpdatedError(BackendError error);
    }

    String getProviderName();

    void createUser(EmailPassword emailPassword, CreateUserCallback callback);

    void addProfile(User user, AddProfileCallback callback);

    void getProfile(User user, GetProfileCallback callback);

    void updateProfile(User user, UpdateProfileCallback callback);
}
