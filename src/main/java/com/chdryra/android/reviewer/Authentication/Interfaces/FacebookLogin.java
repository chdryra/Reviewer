/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.FacebookLoginCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FacebookLogin extends AuthenticationProvider<FacebookLoginCallback> {
    String NAME = "FacebookLogin";

    @Override
    void requestAuthentication(FacebookLoginCallback resultListener);

    @Override
    String getName();
}
