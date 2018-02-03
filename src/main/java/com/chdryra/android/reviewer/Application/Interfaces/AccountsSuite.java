/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Application.Interfaces;

import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserSession;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2016
 * Email: rizwan.choudrey@gmail.com
 */

public interface AccountsSuite {
    UserAuthenticator getAuthenticator();

    UserAccounts getUserAccounts();

    UserSession getUserSession();

    void logout();
}
