/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 16/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface UsersManager {
    UserAuthenticator getAuthenticator();

    UserAccounts getAccounts();

    AuthorsRepository getAuthorsRepository();

    void getCurrentUsersAccount(UserAccounts.GetAccountCallback callback);

    void logoutCurrentUser();
}
