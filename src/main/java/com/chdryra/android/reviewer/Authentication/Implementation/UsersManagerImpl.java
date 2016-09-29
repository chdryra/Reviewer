/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Application.Interfaces.ApplicationInstance;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.NullUserAccount;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.UsersManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 09/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class UsersManagerImpl implements UsersManager {
    private final UserAuthenticator mAuthenticator;
    private final UserAccounts mAccounts;
    private final AuthorsRepository mRepository;

    public UsersManagerImpl(UserAuthenticator authenticator, UserAccounts accounts, AuthorsRepository repository) {
        mAuthenticator = authenticator;
        mAccounts = accounts;
        mRepository = repository;
    }

    @Override
    public UserAuthenticator getAuthenticator() {
        return mAuthenticator;
    }

    @Override
    public UserAccounts getAccounts() {
        return mAccounts;
    }

    @Override
    public void getCurrentUsersAccount(UserAccounts.GetAccountCallback callback) {
        AuthenticatedUser user = mAuthenticator.getAuthenticatedUser();
        if(user != null ) {
            mAccounts.getAccount(user, callback);
        } else {
            callback.onAccount(new NullUserAccount(),
                    new AuthenticationError(ApplicationInstance.APP_NAME,
                            AuthenticationError.Reason.NO_AUTHENTICATED_USER));
        }
    }

    @Override
    public void logoutCurrentUser() {
        mAuthenticator.logout();
    }

    @Override
    public AuthorsRepository getAuthorsRepository() {
        return mRepository;
    }
}
