/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Application.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.NullUserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UsersManager {
    private final UserAuthenticator mAuthenticator;
    private final UserAccounts mAccounts;

    public UsersManager(UserAuthenticator authenticator, UserAccounts accounts) {
        mAuthenticator = authenticator;
        mAccounts = accounts;
    }

    public UserAuthenticator getAuthenticator() {
        return mAuthenticator;
    }

    public UserAccounts getAccounts() {
        return mAccounts;
    }

    public void getCurrentUsersAccount(UserAccounts.GetAccountCallback callback) {
        AuthenticatedUser user = mAuthenticator.getAuthenticatedUser();
        if(user != null ) {
            mAccounts.getAccount(user, callback);
        } else {
            callback.onAccount(new NullUserAccount(),
                    new AuthenticationError(ApplicationInstance.APP_NAME, AuthenticationError.Reason.NO_AUTHENTICATED_USER));
        }
    }

    public void logoutCurrentUser() {
        mAuthenticator.logout();
    }
}
