/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Interfaces;

import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.LoginFailure;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.LoginSuccess;

/**
 * Created by: Rizwan Choudrey
 * On: 02/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface LoginResultHandler {
    void onSuccess(LoginSuccess<?> loginSuccess);

    void onFailure(LoginFailure<?> loginFailure);
}
