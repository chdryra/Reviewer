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

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLoginCallback;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TwitterLoginAndroid extends Callback<TwitterSession> implements
        ActivityResultListener, TwitterLogin {
    private TwitterLoginCallback mListener;
    private final TwitterAuthClient mTwitterAuthClient;
    private final Activity mActivity;

    public TwitterLoginAndroid(Activity activity) {
        mActivity = activity;
        mTwitterAuthClient = new TwitterAuthClient();
    }

    private void setListener(TwitterLoginCallback listener) {
        mListener = listener;
    }

    @Override
    public void requestSignIn(TwitterLoginCallback resultListener) {
        setListener(resultListener);
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
        Twitter.logOut();
        callback.onLoggedOut(CallbackMessage.ok());
    }

    @Override
    public void success(Result<TwitterSession> result) {
        if(mListener != null) mListener.onSuccess(result);
    }

    @Override
    public void failure(TwitterException e) {
        if(mListener != null) mListener.onFailure(e);
    }
}
