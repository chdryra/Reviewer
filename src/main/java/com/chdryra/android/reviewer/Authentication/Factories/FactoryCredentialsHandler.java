/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Factories;

import com.chdryra.android.reviewer.Authentication.Implementation.CredentialsHandlerFacebook;
import com.chdryra.android.reviewer.Authentication.Implementation.CredentialsHandlerTwitter;
import com.chdryra.android.reviewer.Authentication.Implementation.CredentialsHandlerGoogle;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsHandler;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryCredentialsHandler {

    public CredentialsHandler newHandler(FacebookLogin provider,
                                         CredentialsCallback<AccessToken> callback) {
        return new CredentialsHandlerFacebook(provider, callback);
    }

    public CredentialsHandler newHandler(GoogleLogin provider,
                                               CredentialsCallback<GoogleSignInAccount> callback) {
        return new CredentialsHandlerGoogle(provider, callback);
    }

    public CredentialsHandler newHandler(TwitterLogin provider,
                                                CredentialsCallback<TwitterSession> callback) {
        return new CredentialsHandlerTwitter(provider, callback);
    }
}
