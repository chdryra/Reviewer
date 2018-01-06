/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.LoginProviders;


import android.app.Fragment;

import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPasswordLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.GoogleLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;
import com.chdryra.android.reviewer.Utils.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactorySessionProviders {
    public EmailPasswordLogin newEmailPasswordLogin(EmailPassword emailPassword) {
        return new EmailPasswordLoginAndroid(emailPassword);
    }

    public FacebookLogin newFacebookLogin(Fragment fragment) {
        return new FacebookLoginAndroid(fragment);
    }

    public TwitterLogin newTwitterLogin(Fragment fragment) {
        return new TwitterLoginAndroid(fragment.getActivity());
    }

    public GoogleLogin newGoogleLogin(Fragment fragment) {
        return new GoogleLoginAndroid(fragment.getActivity());
    }
}
