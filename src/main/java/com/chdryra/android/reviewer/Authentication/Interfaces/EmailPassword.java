/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Interfaces;

import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.Password;

/**
 * Created by: Rizwan Choudrey
 * On: 28/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface EmailPassword {
    EmailAddress getEmail();

    Password getPassword();

    boolean isValid();
}
