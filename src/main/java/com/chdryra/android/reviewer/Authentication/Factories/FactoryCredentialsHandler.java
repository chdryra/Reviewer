/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Implementation.FacebookCredentials;
import com.chdryra.android.reviewer.Authentication.Implementation.GoogleCredentials;
import com.chdryra.android.reviewer.Authentication.Implementation.TwitterCredentials;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryCredentialsHandler {
    public FacebookCredentials newHandler(FacebookLogin provider, AuthenticatorCallback callback) {
        return new FacebookCredentials(provider, callback);
    }

    public GoogleCredentials newHandler(GoogleLogin provider, AuthenticatorCallback callback) {
        return new GoogleCredentials(provider, callback);
    }

    public TwitterCredentials newHandler(TwitterLogin provider, AuthenticatorCallback callback) {
        return new TwitterCredentials(provider, callback);
    }
}