/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.reviewer.Social.Interfaces.AccessTokenGetter;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformAuthUi;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformAuthUiDefault<T> implements SocialPlatformAuthUi {
    private static final int AUTHORISATION = RequestCodeGenerator.getCode("PlatformAuthorisation");

    private Activity mActivity;
    private LaunchableUi mAuthorisationUi;
    private LaunchableUiLauncher mLauncher;
    private SocialPlatform<T> mPlatform;
    private AccessTokenGetter<T> mGetter;
    private AuthorisationListener mListener;

    public SocialPlatformAuthUiDefault(Activity activity, LaunchableUi authorisationUi,
                                       LaunchableUiLauncher launcher,
                                       SocialPlatform<T> platform,
                                       AuthorisationListener listener,
                                       AccessTokenGetter<T> getter) {
        mActivity = activity;
        mAuthorisationUi = authorisationUi;
        mLauncher = launcher;
        mPlatform = platform;
        mGetter = getter;
        mListener = listener;
    }

    @Override
    public void launchAuthorisationUi() {
        Bundle args = new Bundle();
        args.putString(mAuthorisationUi.getLaunchTag(), mPlatform.getName());
        mLauncher.launch(mAuthorisationUi, mActivity, AUTHORISATION, args);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AUTHORISATION) {
            T accessToken = mGetter.getAccessToken();
            if (accessToken != null) {
                mPlatform.setAccessToken(accessToken);
                mListener.onAuthorisationGiven(mPlatform);
            } else {
                mListener.onAuthorisationRefused(mPlatform);
            }
        }
    }
}
