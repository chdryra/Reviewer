/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.AsyncUtils.BinaryResultCallback;
import com.chdryra.android.startouch.Social.Interfaces.AuthorisationTokenGetter;
import com.chdryra.android.startouch.Social.Interfaces.LoginUi;
import com.chdryra.android.startouch.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.PersonBuffer;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformGoogle extends SocialPlatformBasic<String>
        implements ResultCallback<People.LoadPeopleResult> {
    public static final String NAME = "google+";
    private final GoogleSignInOptions mSignInOptions;
    private final GoogleApiClient mGoogleApiClient;
    private FollowersListener mListener;

    public PlatformGoogle(Context context, PublisherGoogle publisher) {
        super(publisher);
        mSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Plus.SCOPE_PLUS_LOGIN)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mSignInOptions)
                .addApi(Plus.API)
                .build();
    }

    public GoogleSignInOptions getSignInOptions() {
        return mSignInOptions;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public GoogleSignInResultCallback newSignInResultHandler() {
        return new GoogleSignInResultCallback();
    }

    @Override
    public LoginUi getLoginUi(LaunchableUi loginLaunchable, PlatformAuthoriser.Callback listener) {
        return new LoginUiDefault<>(loginLaunchable, this, listener,
                new AuthorisationTokenGetter<String>() {
                    @Override
                    public String getAuthorisationToken() {
                        return PlatformGoogle.this.getAccessToken();
                    }
                });
    }

    @Override
    public void getFollowers(FollowersListener listener) {
        if (mGoogleApiClient.isConnected()) {
            mListener = listener;
            Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
        }
    }

    @Override
    public void onResult(@NonNull People.LoadPeopleResult result) {
        if (result.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = result.getPersonBuffer();
            try {
                mListener.onNumberFollowers(personBuffer.getCount());
            } finally {
                personBuffer.release();
            }
        } else {
            mListener.onNumberFollowers(0);
        }

        mListener = null;
    }

    @Override
    public void logout() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private class GoogleSignInResultCallback
            implements BinaryResultCallback<GoogleSignInResult, GoogleSignInResult> {

        @Override
        public void onSuccess(GoogleSignInResult result) {
            GoogleSignInAccount signInAccount = result.getSignInAccount();
            if (signInAccount != null) {
                String id = signInAccount.getId();
                if (id != null) setAuthorisation(id);
            }
        }

        @Override
        public void onFailure(GoogleSignInResult result) {

        }
    }
}
