/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Implementation;

import android.util.Log;

import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.LoginResultHandler;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by: Rizwan Choudrey
 * On: 02/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleLoginResultHandler implements LoginResultHandler {
    private static final String TAG = "GoogleLoginHandler";

    private PlatformGoogle mPlatform;

    public GoogleLoginResultHandler(PlatformGoogle platform) {
        mPlatform = platform;
    }

    @Override
    public void onSuccess(LoginSuccess<?> loginSuccess) {
        LoginSuccess<GoogleSignInResult> success;
        try {
            success = (LoginSuccess<GoogleSignInResult>) loginSuccess;
        } catch (ClassCastException e) {
            Log.e(TAG, "Not a GoogleSignInResult type", e);
            return;
        }

        GoogleSignInResult result = success.getResult();
        GoogleSignInAccount signInAccount = result.getSignInAccount();
        if(signInAccount != null) {
            String id = signInAccount.getId();
            if(id != null) mPlatform.setAccessToken(id);
        }
    }

    @Override
    public void onFailure(LoginFailure<?> loginFailure) {

    }
}
