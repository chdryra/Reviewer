/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface GoogleLogin extends SessionProvider<GoogleLoginCallback> {
    GoogleSignInOptions DEFAULT_SIGN_IN = GoogleSignInOptions.DEFAULT_SIGN_IN;
    String NAME = "GoogleSignIn";
}
