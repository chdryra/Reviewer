/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import com.chdryra.android.mygenerallibrary.AsyncUtils.BinaryResultCallback;
import com.chdryra.android.startouch.Utils.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 26/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LoginEmailPassword extends LoginProvider<LoginEmailPassword.Callback> {
    String NAME = "EmailPasswordLogin";

    interface Callback extends BinaryResultCallback<EmailPassword, String> {
    }
}
