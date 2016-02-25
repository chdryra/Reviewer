/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Factories;

import android.app.Activity;

import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PlatformTwitter;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformAuthUi;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorisationUi {
    private LaunchableUi mDefaultUi;
    private LaunchableUi mFacebookTwitterUi;
    private LaunchableUiLauncher mLauncher;

    public FactoryAuthorisationUi(LaunchableUi defaultUi,
                                  LaunchableUi facebookTwitterUi,
                                  LaunchableUiLauncher launcher) {
        mDefaultUi = defaultUi;
        mFacebookTwitterUi = facebookTwitterUi;
        mLauncher = launcher;
    }

    public SocialPlatformAuthUi newAuthorisationUi(Activity activity,
                                                            SocialPlatform<?> platform,
                                                            AuthorisationListener
                                                                    listener) {
        boolean fb_twittter = platform.getName().equals(PlatformFacebook.NAME)
                || platform.getName().equals(PlatformTwitter.NAME);
        LaunchableUi ui = fb_twittter ? mFacebookTwitterUi : mDefaultUi;
        return platform.getAuthUi(activity, ui, mLauncher, listener);
    }

}
