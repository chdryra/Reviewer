/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.AuthenticationProvider;
import com.chdryra.android.reviewer.Authentication.Interfaces.BinaryResultCallback;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class GoogleLogin implements AuthenticationProvider {
    private static final String NAME = "GoogleSignIn";

    @Override
    public void requestAuthentication(BinaryResultCallback resultListener) {

    }

    @Override
    public String getName() {
        return NAME;
    }
}
