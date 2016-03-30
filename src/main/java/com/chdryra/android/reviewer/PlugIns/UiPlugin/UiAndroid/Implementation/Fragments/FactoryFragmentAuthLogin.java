/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Fragments;

import android.app.Fragment;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PlatformGoogle;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PlatformTwitter;

/**
 * Created by: Rizwan Choudrey
 * On: 02/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryFragmentAuthLogin {
    @Nullable
    public Fragment newFragment(String platform) {
        if (platform.equals(PlatformFacebook.NAME)) {
            return new FragmentFacebookLogin();
        } else if (platform.equals(PlatformGoogle.NAME)) {
            return new FragmentGoogleLogin();
        } else if (platform.equals(PlatformTwitter.NAME)) {
            return new FragmentTwitterLogin();
        } else {
            return null;
        }
    }
}
