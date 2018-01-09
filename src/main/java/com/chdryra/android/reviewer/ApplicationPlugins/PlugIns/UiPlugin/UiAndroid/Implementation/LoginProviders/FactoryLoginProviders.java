/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.UiPlugin.UiAndroid.Implementation.LoginProviders;


import android.app.Fragment;

import com.chdryra.android.reviewer.Authentication.Interfaces.LoginEmailPassword;
import com.chdryra.android.reviewer.Authentication.Interfaces.LoginFacebook;
import com.chdryra.android.reviewer.Authentication.Interfaces.LoginGoogle;
import com.chdryra.android.reviewer.Authentication.Interfaces.LoginTwitter;
import com.chdryra.android.reviewer.Utils.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLoginProviders {
    public LoginEmailPassword newEmailPasswordLogin(EmailPassword emailPassword) {
        return new LoginEmailPasswordAndroid(emailPassword);
    }

    public LoginFacebook newFacebookLogin(Fragment fragment) {
        return new LoginFacebookAndroid(fragment);
    }

    public LoginTwitter newTwitterLogin(Fragment fragment) {
        return new LoginTwitterAndroid(fragment.getActivity());
    }

    public LoginGoogle newGoogleLogin(Fragment fragment) {
        return new LoginGoogleAndroid(fragment.getActivity());
    }
}
