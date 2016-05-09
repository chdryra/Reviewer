/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation;


import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Interfaces.BackendUsersDb;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.reviewer.Authentication.Implementation.AuthorProfile;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;

/**
 * Created by: Rizwan Choudrey
 * On: 06/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendUserAccounts implements UserAccounts {
    private BackendUsersDb mDb;
    private UserProfileTranslator mUsersFactory;

    public BackendUserAccounts(BackendUsersDb db, UserProfileTranslator usersFactory) {
        mDb = db;
        mUsersFactory = usersFactory;
    }

    @Override
    public void createUser(EmailPassword emailPassword, final CreateUserCallback callback) {
        mDb.createUser(emailPassword, new BackendUsersDb.CreateUserCallback() {
            @Override
            public void onUserCreated(User user) {
                callback.onUserCreated(mUsersFactory.toAuthenticatedUser(user),
                        CallbackMessage.ok("User created"));
            }

            @Override
            public void onUserCreationError(BackendError error) {
                callback.onUserCreated(mUsersFactory.newNullAuthenticatedUser(),
                        CallbackMessage.error("Error creating user: " + error.getMessage()));
            }
        });
    }

    @Override
    public void addProfile(AuthenticatedUser user, final AuthorProfile profile,
                           final AddProfileCallback callback) {
        mDb.addProfile(mUsersFactory.toUser(user, profile),
                new BackendUsersDb.AddProfileCallback() {
            @Override
            public void onProfileAdded(User user) {
                callback.onProfileAdded(profile, CallbackMessage.ok("Profile added"));
            }

            @Override
            public void onProfileAddedError(BackendError error) {
                callback.onProfileAdded(profile,
                        CallbackMessage.error("Problem adding profile: " + error.getMessage()));
            }
        });
    }

    @Override
    public void getProfile(AuthenticatedUser user, final GetProfileCallback callback) {
        mDb.getProfile(mUsersFactory.toUser(user), new BackendUsersDb.GetProfileCallback() {
            @Override
            public void onProfile(Profile profile) {
                callback.onProfile(mUsersFactory.toAuthorProfile(profile),
                        CallbackMessage.ok("Profile retrieved"));
            }

            @Override
            public void onProfileError(BackendError error) {
                callback.onProfile(mUsersFactory.newNullAuthorProfile(),
                        CallbackMessage.error("Error retrieving profile: " + error.getMessage()));
            }
        });
    }
}
