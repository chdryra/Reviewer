/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

/**
 * Created by: Rizwan Choudrey
 * On: 21/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface AuthenticationListener
        extends BinaryResultListener<CallbackMessage, CallbackMessage> {
    @Override
    void onSuccess(CallbackMessage result);

    @Override
    void onFailure(CallbackMessage result);
}
