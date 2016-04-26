/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLoginCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface TwitterLogin extends AuthenticationProvider<TwitterLoginCallback> {
    String NAME = "TwitterLogin";

    @Override
    void requestAuthentication(TwitterLoginCallback resultListener);

    @Override
    String getName();
}
