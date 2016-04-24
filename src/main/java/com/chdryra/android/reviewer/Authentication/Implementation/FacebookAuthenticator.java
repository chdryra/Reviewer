/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLoginCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FacebookAuthenticator implements FacebookLoginCallback {
    private AuthenticationCallback mCallback;

    public FacebookAuthenticator(AuthenticationCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onSuccess(LoginResult result) {
        mCallback.onSuccess(CallbackMessage.ok(FacebookLogin.NAME));
    }

    @Override
    public void onFailure(FacebookException result) {
        mCallback.onSuccess(CallbackMessage.error(FacebookLogin.NAME));
    }
}
