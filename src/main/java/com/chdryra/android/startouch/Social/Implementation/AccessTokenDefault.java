/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AccessTokenDefault {
    private final String mToken;
    private final String mSecret;

    public AccessTokenDefault(String token, String secret) {
        mToken = token;
        mSecret = secret;
    }

    public String getToken() {
        return mToken;
    }

    public String getSecret() {
        return mSecret;
    }
}
