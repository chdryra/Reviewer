/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FacebookLogin extends LoginProvider<FacebookLogin.Callback> {
    String PERMISSION = PlatformFacebook.REQUIRED_PERMISSION;
    String NAME = "FacebookLogin";

    interface Callback extends BinaryResultCallback<LoginResult, FacebookException> {
    }
}
