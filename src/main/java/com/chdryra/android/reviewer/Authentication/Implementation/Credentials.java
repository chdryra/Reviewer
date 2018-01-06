/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class Credentials<Cred> {
    private final String mProvider;
    private final Cred mCredentials;

    public Credentials(String provider, Cred credentials) {
        mProvider = provider;
        mCredentials = credentials;
    }

    public String getProvider() {
        return mProvider;
    }

    Cred getCredentials() {
        return mCredentials;
    }
}
