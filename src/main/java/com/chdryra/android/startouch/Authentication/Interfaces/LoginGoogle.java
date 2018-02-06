/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import com.chdryra.android.mygenerallibrary.AsyncUtils.BinaryResultCallback;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LoginGoogle extends LoginProvider<LoginGoogle.Callback> {
    GoogleSignInOptions DEFAULT_SIGN_IN = GoogleSignInOptions.DEFAULT_SIGN_IN;
    String NAME = "GoogleSignIn";

    interface Callback extends BinaryResultCallback<GoogleSignInResult, GoogleSignInResult> {

    }
}
