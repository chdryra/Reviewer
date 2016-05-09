/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

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
}
