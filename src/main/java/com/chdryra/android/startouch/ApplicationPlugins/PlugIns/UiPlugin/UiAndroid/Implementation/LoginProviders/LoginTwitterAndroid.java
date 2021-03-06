/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid
        .Implementation.LoginProviders;

import android.app.Activity;
import android.content.Intent;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.startouch.Authentication.Interfaces.LoginTwitter;
import com.chdryra.android.startouch.Presenter.Interfaces.View.ActivityResultListener;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LoginTwitterAndroid extends com.twitter.sdk.android.core.Callback<TwitterSession>
        implements
        ActivityResultListener, LoginTwitter {
    private final TwitterAuthClient mTwitterAuthClient;
    private final Activity mActivity;
    private Callback mListener;

    public LoginTwitterAndroid(Activity activity) {
        mActivity = activity;
        mTwitterAuthClient = new TwitterAuthClient();
    }

    @Override
    public void login(Callback loginLoginCallback) {
        setListener(loginLoginCallback);
        mTwitterAuthClient.authorize(mActivity, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void logout(LogoutCallback callback) {
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
        callback.onLoggedOut(CallbackMessage.ok());
    }

    @Override
    public void success(Result<TwitterSession> result) {
        if (mListener != null) mListener.onSuccess(result);
    }

    @Override
    public void failure(TwitterException e) {
        if (mListener != null) mListener.onFailure(e);
    }

    private void setListener(Callback listener) {
        mListener = listener;
    }
}
