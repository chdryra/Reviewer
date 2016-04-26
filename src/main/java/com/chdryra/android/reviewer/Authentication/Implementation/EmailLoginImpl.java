/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.EmailLogin;
import com.chdryra.android.reviewer.Authentication.Interfaces.EmailPassword;
import com.chdryra.android.reviewer.Utils.EmailAddress;
import com.chdryra.android.reviewer.Utils.Password;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class EmailLoginImpl implements EmailLogin {
    private String mProvider;
    private EmailPassword mGetter;

    public EmailLoginImpl(String provider, EmailPassword getter) {
        mProvider = provider;
        mGetter = getter;
    }

    @Override
    public String getName() {
        return mProvider;
    }

    protected EmailAddress getEmail() {
        return mGetter.getEmail();
    }

    protected Password getPassword() {
        return mGetter.getPassword();
    }
}
