/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.mygenerallibrary.ActivitySingleFragment;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments
        .FragmentFacebookLogin;
import com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments
        .FragmentTwitterLogin;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PlatformTwitterFabric;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LauncherUi;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ActivitySocialLogin extends ActivitySingleFragment
        implements FragmentFacebookLogin.FacebookLoginListener,
        FragmentTwitterLogin.TwitterLoginListener, LaunchableUi {
    private static final String TAG = "ActivitySocialLogin";
    private static final String KEY = "ActivitySocialLogin.platform";

    private Fragment mFragment;

    @Override
    protected Fragment createFragment() {
        String platform = getBundledPlatform();
        if (platform.equals(PlatformFacebook.NAME)) {
            return new FragmentFacebookLogin();
        } else if (platform.equals(PlatformTwitterFabric.NAME)) {
            mFragment = new FragmentTwitterLogin();
            return mFragment;
        }

        return null;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        success();
    }

    @Override
    public void onError(FacebookException error) {
        failure();
    }

    @Override
    public void onSuccess(Result<TwitterSession> result) {
        success();
    }

    @Override
    public void onFailure(TwitterException error) {
        failure();
    }

    @Override
    public String getLaunchTag() {
        return TAG;
    }

    @Override
    public void launch(LauncherUi launcher) {
        launcher.launch(getClass(), KEY);
    }

    private String getBundledPlatform() {
        Bundle args = getIntent().getBundleExtra(KEY);
        if (args == null) throwError();
        String platform = args.getString(TAG);
        if (platform == null || platform.length() == 0) throwError();

        return platform;
    }

    private void success() {
        setResult(RESULT_OK);
        finish();
    }

    private void failure() {
        setResult(RESULT_FIRST_USER);
        finish();
    }

    private String throwError() {
        throw new RuntimeException("No platform specified!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mFragment != null) {
            mFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
