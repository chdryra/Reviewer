/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

import com.chdryra.android.startouch.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.startouch.Authentication.Interfaces.UserAuthenticator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/05/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AccountsManagerImpl implements AccountsManager {
    private final UserAuthenticator mAuthenticator;
    private final UserAccounts mAccounts;

    public AccountsManagerImpl(UserAuthenticator authenticator, UserAccounts accounts) {
        mAuthenticator = authenticator;
        mAccounts = accounts;
    }

    @Override
    public UserAuthenticator getAuthenticator() {
        return mAuthenticator;
    }

    @Override
    public UserAccounts getAccounts() {
        return mAccounts;
    }
}
