/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Factories;

import android.app.Activity;

import com.chdryra.android.reviewer.Social.Implementation.DefaultOAuthSeeker;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation.ParcelablePacker;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;
import com.chdryra.android.reviewer.Social.Implementation.PlatformAuthorisationSeekerBasic;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthorisationSeeker;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.View.LauncherModel.Factories.LaunchableUiLauncher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.facebook.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorisationSeeker {
    private LaunchableUi mDefaultUi;
    private LaunchableUi mFacebookUi;
    private LaunchableUiLauncher mLauncher;

    public FactoryAuthorisationSeeker(LaunchableUi defaultUi, LaunchableUi facebookUi,
                                      LaunchableUiLauncher launcher) {
        mDefaultUi = defaultUi;
        mFacebookUi= facebookUi;
        mLauncher = launcher;
    }

    public PlatformAuthorisationSeeker newAuthorisationSeeker(Activity activity,
                                                              SocialPlatform<?> platform,
                                                              PlatformAuthoriser.AuthorisationListener listener) {
        if(platform.getName().equals(PlatformFacebook.NAME)) {
            return new FacebookSeeker(activity, mFacebookUi, mLauncher,
                    (SocialPlatform<AccessToken>) platform, listener);
        } else {
            return new DefaultOAuthSeeker<>(activity, mDefaultUi, mLauncher, platform,
                    listener, new ParcelablePacker<OAuthRequest>());
        }
    }

    private static class FacebookSeeker extends PlatformAuthorisationSeekerBasic<AccessToken> {
        public FacebookSeeker(Activity activity, LaunchableUi authorisationUi,
                              LaunchableUiLauncher launcher,
                              SocialPlatform<AccessToken> platform,
                              PlatformAuthoriser.AuthorisationListener listener) {
            super(activity, authorisationUi, launcher, platform, listener);
        }

        @Override
        protected AccessToken getAccessToken() {
            return AccessToken.getCurrentAccessToken();
        }
    }
}
