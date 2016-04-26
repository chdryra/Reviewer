/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import android.app.Activity;
import android.content.Intent;

import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLoginCallback;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleLoginAndroid implements ActivityResultListener, GoogleLogin {
    private static final int GOOGLE_SIGN_IN = RequestCodeGenerator.getCode("GoogleSignIn");
    private static final String NAME = "GoogleSignIn";
    private GoogleApiClient mGoogleApiClient;
    private GoogleLoginCallback mListener;
    private Activity mActivity;

    public GoogleLoginAndroid(Activity activity) {
        mActivity = activity;

        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();

        mGoogleApiClient = new GoogleApiClient
                .Builder(mActivity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
    }

    public void setListener(GoogleLoginCallback listener) {
        mListener = listener;
    }

    @Override
    public void requestAuthentication(GoogleLoginCallback resultListener) {
        setListener(resultListener);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mActivity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GOOGLE_SIGN_IN && mListener != null) {
            notifyListener(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    private void notifyListener(GoogleSignInResult result) {
        if (result.isSuccess()) {
            mListener.onSuccess(result);
        } else {
            mListener.onFailure(result);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }
}
