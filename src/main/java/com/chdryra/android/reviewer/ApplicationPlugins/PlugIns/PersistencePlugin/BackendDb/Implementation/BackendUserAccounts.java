/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendDb.Implementation;


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

    public BackendUserAccounts(BackendUsersDb db) {
        mDb = db;
    }

    @Override
    public void createUser(EmailPassword emailPassword, AddProfileCallback callback) {

    }

    @Override
    public void addProfile(AuthenticatedUser user, AuthorProfile profile, AddProfileCallback
            callback) {

    }

    @Override
    public void getProfile(AuthenticatedUser user, GetProfileCallback callback) {

    }
}
