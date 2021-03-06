/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 16/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AccountsManager {
    UserAuthenticator getAuthenticator();

    UserAccounts getAccounts();
}
