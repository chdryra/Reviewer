/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Factories;

import android.app.Activity;

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
    private LaunchableUi mUi;
    private LaunchableUi mFacebookTwitterUi;
    private LaunchableUiLauncher mLauncher;

    public FactoryAuthorisationUi(LaunchableUi ui,
                                  LaunchableUiLauncher launcher) {
        mUi = ui;
        mLauncher = launcher;
    }

    public SocialPlatformAuthUi newAuthorisationUi(Activity activity,
                                                            SocialPlatform<?> platform,
                                                            AuthorisationListener
                                                                    listener) {
        return platform.getAuthUi(activity, mUi, mLauncher, listener);
    }

}
