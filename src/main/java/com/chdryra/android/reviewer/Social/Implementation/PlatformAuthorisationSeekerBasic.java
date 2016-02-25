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

import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthorisationSeeker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ActivityResultListener;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Utils.RequestCodeGenerator;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PlatformAuthorisationSeekerBasic<T> implements PlatformAuthorisationSeeker,
        ActivityResultListener{
    private static final int AUTHORISATION = RequestCodeGenerator.getCode("FacebookAuthorisation");

    private Activity mActivity;
    private LaunchableUi mAuthorisationUi;
    private LaunchableUiLauncher mLauncher;
    private SocialPlatform<T> mPlatform;
    private PlatformAuthoriser.AuthorisationListener mListener;

    protected abstract T getAccessToken();

    public PlatformAuthorisationSeekerBasic(Activity activity, LaunchableUi authorisationUi,
                                            LaunchableUiLauncher launcher,
                                            SocialPlatform<T> platform,
                                            PlatformAuthoriser.AuthorisationListener listener) {
        mActivity = activity;
        mAuthorisationUi = authorisationUi;
        mLauncher = launcher;
        mPlatform = platform;
        mListener = listener;
    }

    @Override
    public void seekAuthorisation() {
        mLauncher.launch(mAuthorisationUi, mActivity, AUTHORISATION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AUTHORISATION) {
            T currentAccessToken = getAccessToken();
            if (currentAccessToken != null) {
                mPlatform.setAccessToken(currentAccessToken);
                mListener.onAuthorisationGiven(mPlatform);
            } else {
                mListener.onAuthorisationRefused(mPlatform);
            }
        }
    }
}
