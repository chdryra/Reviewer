/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.LoginFacebook;
import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CredentialsProviderFacebook extends CredentialsProviderBasic<AccessToken, LoginFacebook.Callback>
        implements LoginFacebook.Callback {

    public CredentialsProviderFacebook(LoginFacebook login) {
        super(login);
    }

    @Override
    public void onSuccess(LoginResult result) {
        notifyOnSuccess(result.getAccessToken());
    }

    @Override
    public void onFailure(FacebookException result) {
        notifyOnFailure(result.getMessage());
    }

    @Override
    protected LoginFacebook.Callback getProviderCallback() {
        return this;
    }
}
