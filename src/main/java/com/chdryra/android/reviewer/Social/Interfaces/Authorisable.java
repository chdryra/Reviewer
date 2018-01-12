/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface Authorisable<Token> {
    interface AuthorisationListener {
        void onAuthorised(boolean isAuthorised);
    }

    boolean isAuthorised();

    void setAuthorisation(Token token);

    void setAuthListener(AuthorisationListener listener);
}
