/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;


/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthenticatedUser {
    private String mProvider;
    private String mUserId;

    public AuthenticatedUser(String provider, String userId) {
        mProvider = provider;
        mUserId = userId;
    }

    public String getProvider() {
        return mProvider;
    }

    public String getUserId() {
        return mUserId;
    }
}
