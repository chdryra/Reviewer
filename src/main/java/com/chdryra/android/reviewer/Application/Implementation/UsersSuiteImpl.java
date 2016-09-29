/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Implementation;

import com.chdryra.android.reviewer.Application.Interfaces.UsersSuite;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class UsersSuiteImpl implements UsersSuite {
    private UserAuthenticator mAuthenticator;
    private UserSession mSession;
    private UserAccounts mAccounts;


    @Override
    public UserSession getUserSession() {
        return mSession;
    }

    @Override
    public UserAccounts getUserAccounts() {
        return mAccounts;
    }

    @Override
    public void logout() {

    }
}
