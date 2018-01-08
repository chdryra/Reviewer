/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.LoginEmailPassword;
import com.chdryra.android.reviewer.Utils.EmailPassword;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2018
 * Email: rizwan.choudrey@gmail.com
 */

public class CredentialsProviderEmailPassword extends
        CredentialsProviderBasic<EmailPassword, LoginEmailPassword.Callback>
        implements LoginEmailPassword.Callback {

    public CredentialsProviderEmailPassword(LoginEmailPassword login) {
        super(login);
    }

    @Override
    public void onSuccess(EmailPassword emailPassword) {
        notifyOnSuccess(emailPassword);
    }

    @Override
    public void onFailure(String error) {
        notifyOnFailure(error);
    }

    @Override
    protected LoginEmailPassword.Callback getProviderCallback() {
        return this;
    }
}
