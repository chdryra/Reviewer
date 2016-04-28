/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLoginCallback;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CredentialsHandlerGoogle extends CredentialsHandlerBasic<GoogleSignInAccount, GoogleLoginCallback>
        implements GoogleLoginCallback {
    public CredentialsHandlerGoogle(GoogleLogin provider, CredentialsCallback<GoogleSignInAccount> callback) {
        super(provider, callback);
    }

    @Override
    protected GoogleLoginCallback getProviderCallback() {
        return this;
    }

    @Override
    public void onSuccess(GoogleSignInResult result) {
        GoogleSignInAccount signInAccount = result.getSignInAccount();
        if(signInAccount != null) {
            notifyOnSuccess(getProviderName(), signInAccount);
        } else {
            onFailure(result);
        }
    }

    @Override
    public void onFailure(GoogleSignInResult result) {
        String providerName = getProviderName();
        AuthenticationError error = new AuthenticationError(providerName,
                AuthenticationError.Reason.PROVIDER_ERROR, result.getStatus().getStatusMessage());
        notifyOnFailure(getProviderName(), error);
    }
}
