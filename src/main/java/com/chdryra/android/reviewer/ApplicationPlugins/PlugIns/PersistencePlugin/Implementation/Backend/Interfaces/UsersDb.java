/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.Profile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.User;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Utils.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UsersDb {
    interface UserConflictCallback {
        void onUserName(String name, @Nullable AuthenticationError error);
    }

    interface CreateUserCallback {
        void onUserCreated(User user);

        void onUserCreationError(AuthenticationError error);
    }

    interface CreateAccountCallback {
        void onProfileAdded(User user);

        void onProfileAddedError(AuthenticationError error);
    }

    interface GetProfileCallback {
        void onProfile(Profile profile);

        void onProfileError(AuthenticationError error);
    }

    interface UpdateProfileCallback {
        void onProfileUpdated(User user);

        void onProfileUpdatedError(AuthenticationError error);
    }

    String getProviderName();

    void checkNameConflict(String authorName, UserConflictCallback callback);

    void createUser(EmailPassword emailPassword, CreateUserCallback callback);

    void addProfile(User user, CreateAccountCallback callback);

    void getProfile(User user, GetProfileCallback callback);

    void updateProfile(User user, UpdateProfileCallback callback);
}
