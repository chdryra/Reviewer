/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces.UsersDb;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticationError;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Utils.EmailPassword;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;

/**
 * Created by: Rizwan Choudrey
 * On: 06/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendUserAccounts implements UserAccounts {
    private UsersDb mDb;
    private UserProfileTranslator mUsersFactory;

    public BackendUserAccounts(UsersDb db, UserProfileTranslator usersFactory) {
        mDb = db;
        mUsersFactory = usersFactory;
    }

    @Override
    public void createUser(EmailPassword emailPassword, final CreateUserCallback callback) {
        mDb.createUser(emailPassword, new UsersDb.CreateUserCallback() {
            @Override
            public void onUserCreated(User user) {
                callback.onUserCreated(mUsersFactory.toAuthenticatedUser(user), null);
            }

            @Override
            public void onUserCreationError(AuthenticationError error) {
                callback.onUserCreated(mUsersFactory.newNullAuthenticatedUser(), error);
            }
        });
    }

    @Override
    public void addProfile(final AuthenticatedUser authUser, final AuthorProfile profile,
                           final AddProfileCallback callback) {
        mDb.addProfile(mUsersFactory.toUser(authUser, profile),
                new UsersDb.AddProfileCallback() {
            @Override
            public void onProfileAdded(User user) {
                callback.onProfileAdded(authUser, profile, null);
            }

            @Override
            public void onProfileAddedError(AuthenticationError error) {
                callback.onProfileAdded(authUser, profile, error);
            }
        });
    }

    @Override
    public void getProfile(final AuthenticatedUser authUser, final GetProfileCallback callback) {
        mDb.getProfile(mUsersFactory.toUser(authUser), new UsersDb.GetProfileCallback() {
            @Override
            public void onProfile(Profile profile) {
                callback.onProfile(authUser, mUsersFactory.toAuthorProfile(profile), null);
            }

            @Override
            public void onProfileError(AuthenticationError error) {
                callback.onProfile(authUser, mUsersFactory.newNullAuthorProfile(), error);
            }
        });
    }

    @Override
    public AuthorProfile newProfie(String name) {
        return mUsersFactory.newProfile(name);
    }
}
