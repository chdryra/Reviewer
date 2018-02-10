/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.content.Intent;
import android.os.Bundle;

import com.chdryra.android.corelibrary.OtherUtils.ActivityResultCode;
import com.chdryra.android.corelibrary.OtherUtils.RequestCodeGenerator;
import com.chdryra.android.startouch.Social.Interfaces.AuthorisationTokenGetter;
import com.chdryra.android.startouch.Social.Interfaces.LoginUi;
import com.chdryra.android.startouch.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.startouch.Social.Interfaces.SocialPlatform;
import com.chdryra.android.startouch.View.LauncherModel.Implementation.UiLauncherArgs;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.UiLauncher;
import com.chdryra.android.startouch.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class LoginUiDefault<T> implements LoginUi {
    private static final int AUTHORISATION = RequestCodeGenerator.getCode(LoginUiDefault.class);

    private final LaunchableUi mAuthorisationUi;
    private final SocialPlatform<T> mPlatform;
    private final AuthorisationTokenGetter<T> mGetter;
    private final PlatformAuthoriser.Callback mListener;

    public LoginUiDefault(LaunchableUi authorisationUi,
                          SocialPlatform<T> platform,
                          PlatformAuthoriser.Callback listener,
                          AuthorisationTokenGetter<T> getter) {
        mAuthorisationUi = authorisationUi;
        mPlatform = platform;
        mGetter = getter;
        mListener = listener;
    }

    @Override
    public void launchUi(UiLauncher launcher) {
        Bundle args = new Bundle();
        args.putString(mAuthorisationUi.getLaunchTag(), mPlatform.getName());
        launcher.launch(mAuthorisationUi, new UiLauncherArgs(AUTHORISATION).setBundle(args));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AUTHORISATION && ActivityResultCode.OK.equals(resultCode)) {
            T accessToken = mGetter.getAuthorisationToken();
            if (accessToken != null) {
                mPlatform.setAuthorisation(accessToken);
                mListener.onAuthorisationGiven(mPlatform);
            } else {
                mListener.onAuthorisationRefused(mPlatform);
            }
        }
    }
}
