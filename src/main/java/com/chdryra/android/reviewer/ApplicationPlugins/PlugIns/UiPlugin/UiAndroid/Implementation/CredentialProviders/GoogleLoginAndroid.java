/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.CredentialProviders;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLoginCallback;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleLoginAndroid implements ActivityResultListener, GoogleLogin, GoogleApiClient.ConnectionCallbacks {
    private static final int GOOGLE_SIGN_IN = RequestCodeGenerator.getCode("GoogleSignIn");
    private static final int GOOGLE_CLIENT_ID = R.string.google_client_id;

    private GoogleApiClient mGoogleApiClient;
    private GoogleLoginCallback mListener;
    private Activity mActivity;

    private LogoutCallback mLogoutCallback;

    public GoogleLoginAndroid(Activity activity) {
        mActivity = activity;

        GoogleSignInOptions options = new GoogleSignInOptions
                .Builder(DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(GOOGLE_CLIENT_ID))
                .requestScopes(new Scope(Scopes.EMAIL))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient
                .Builder(mActivity)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .addConnectionCallbacks(this)
                .build();
    }

    public void setListener(GoogleLoginCallback listener) {
        mListener = listener;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()) {
                    mLogoutCallback.onLoggedOut(CallbackMessage.ok());
                } else {
                    mLogoutCallback.onLoggedOut(CallbackMessage.error(status.getStatusMessage()));
                }
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        mLogoutCallback.onLoggedOut(CallbackMessage.error("Connection suspended"));
    }

    @Override
    public void logout(LogoutCallback callback) {
        mLogoutCallback = callback;
        mGoogleApiClient.connect();
    }

    @Override
    public void requestSignIn(GoogleLoginCallback resultListener) {
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
