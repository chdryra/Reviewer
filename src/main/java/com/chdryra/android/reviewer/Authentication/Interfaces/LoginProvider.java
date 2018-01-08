/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.chdryra.android.mygenerallibrary.AsyncUtils.BinaryResultCallback;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LoginProvider<LoginCb extends BinaryResultCallback> {
    interface LogoutCallback {
        void onLoggedOut(CallbackMessage message);
    }

    String getName();

    void login(LoginCb loginCallback);

    void logout(LogoutCallback callback);
}
