/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.PluginTemp;

import android.app.Activity;
import android.app.Fragment;

import com.chdryra.android.reviewer.Authentication.Interfaces.EmailLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthenticationProviders {
    public EmailLogin newEmailLogin(EmailPassword emailPassword) {
        return new FirebaseEmailLogin(emailPassword);
    }

    public FacebookLogin newFacebookLogin(Fragment fragment) {
        return new FacebookLoginAndroid(fragment);
    }

    public TwitterLogin newTwitterLogin(Activity activity) {
        return new TwitterLoginAndroid(activity);
    }

    public GoogleLogin newGoogleLogin(Activity activity) {
        return new GoogleLoginAndroid(activity);
    }
}
