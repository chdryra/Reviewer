/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.UiPlugin.UiAndroid.Implementation.Utils;

import android.app.Activity;

import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.facebook.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorisationSeeker {
    public PlatformAuthorisationSeeker newAuthorisationSeeker(Activity activity, SocialPlatform<?> platform,
                                                              PlatformAuthoriser.AuthorisationListener listener) {
        if(platform.getName().equals(PlatformFacebook.NAME)) {
            return new FacebookAuthorisationSeeker(activity, (SocialPlatform<AccessToken>) platform, listener);
        } else {
            return new DialogAuthorisationSeeker<>(activity, platform, listener);
        }
    }
}
