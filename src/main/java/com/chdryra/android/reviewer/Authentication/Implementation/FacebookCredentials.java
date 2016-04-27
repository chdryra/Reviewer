/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookCredentialsCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLoginCallback;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FacebookCredentials extends CredentialsHandlerBasic<AccessToken, FacebookLoginCallback>
        implements FacebookLoginCallback {

    public FacebookCredentials(FacebookLogin provider, FacebookCredentialsCallback callback) {
        super(provider, callback);
    }

    @Override
    public void onSuccess(LoginResult result) {
        notifyOnSuccess(getProviderName(), result.getAccessToken());
    }

    @Override
    public void onFailure(FacebookException result) {
        String providerName = getProviderName();
        AuthenticationError error = new AuthenticationError(providerName,
                AuthenticationError.Reason.PROVIDER_ERROR, result.getMessage());
        notifyOnFailure(providerName, error);
    }

    @Override
    protected FacebookLoginCallback getProviderCallback() {
        return this;
    }
}
