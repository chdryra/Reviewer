/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Factories;

import com.chdryra.android.startouch.Authentication.Implementation.CredentialsProviderEmailPassword;
import com.chdryra.android.startouch.Authentication.Implementation.CredentialsProviderFacebook;
import com.chdryra.android.startouch.Authentication.Implementation.CredentialsProviderGoogle;
import com.chdryra.android.startouch.Authentication.Implementation.CredentialsProviderTwitter;
import com.chdryra.android.startouch.Authentication.Interfaces.CredentialsProvider;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginEmailPassword;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginFacebook;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginGoogle;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginTwitter;
import com.chdryra.android.startouch.Utils.EmailPassword;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryCredentialsProvider {

    public FactoryCredentialsProvider() {
    }

    public CredentialsProvider<EmailPassword> newCredentialsProvider(LoginEmailPassword login) {
        return new CredentialsProviderEmailPassword(login);
    }

    public CredentialsProvider<com.facebook.AccessToken> newCredentialsProvider(LoginFacebook login) {
        return new CredentialsProviderFacebook(login);
    }

    public CredentialsProvider<GoogleSignInAccount> newCredentialsProvider(LoginGoogle login) {
        return new CredentialsProviderGoogle(login);
    }

    public CredentialsProvider<TwitterSession> newCredentialsProvider(LoginTwitter login) {
        return new CredentialsProviderTwitter(login);
    }
}
