/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.Fragment;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PlatformGoogle;
import com.chdryra.android.reviewer.Social.Implementation.PlatformTwitter;

/**
 * Created by: Rizwan Choudrey
 * On: 02/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFragmentSocialLogin {
    @Nullable
    public Fragment newFragment(String platform) {
        switch (platform) {
            case PlatformFacebook.NAME:
                return new FragmentFacebookLogin();
            case PlatformGoogle.NAME:
                return new FragmentGoogleLogin();
            case PlatformTwitter.NAME:
                return new FragmentTwitterLogin();
            default:
                return null;
        }
    }
}
