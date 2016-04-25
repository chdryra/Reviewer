/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticatorCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLoginCallback;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleAuthenticator extends AuthenticationHandlerBasic<GoogleLoginCallback>
        implements GoogleLoginCallback {
    public GoogleAuthenticator(GoogleLogin provider, AuthenticatorCallback callback) {
        super(provider, callback);
    }

    @Override
    protected GoogleLoginCallback getProviderCallback() {
        return this;
    }

    @Override
    public void onSuccess(GoogleSignInResult result) {

    }

    @Override
    public void onFailure(GoogleSignInResult result) {

    }
}
