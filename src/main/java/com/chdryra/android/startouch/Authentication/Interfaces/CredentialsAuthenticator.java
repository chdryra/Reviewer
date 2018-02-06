/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Interfaces;

import com.chdryra.android.startouch.Authentication.Implementation.AuthenticatedUser;
import com.chdryra.android.startouch.Authentication.Implementation.AuthenticationError;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2018
 * Email: rizwan.choudrey@gmail.com
 */
public interface CredentialsAuthenticator<Cred> {
    interface Callback {
        void onAuthenticated(AuthenticatedUser user);

        void onAuthenticationError(AuthenticationError error);
    }

    void authenticate(Cred credentials, Callback callback);
}
