/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Authentication.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.CredentialsCallback;
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLogin;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CredentialsProviderTwitter extends CredentialsProviderBasic<TwitterSession, TwitterLogin.Callback>
        implements TwitterLogin.Callback {

    public CredentialsProviderTwitter(TwitterLogin provider, CredentialsCallback<TwitterSession> callback) {
        super(provider, callback);
    }

    @Override
    protected TwitterLogin.Callback getProviderCallback() {
        return this;
    }

    @Override
    public void onSuccess(Result<TwitterSession> result) {
        notifyOnSuccess(result.data);
    }

    @Override
    public void onFailure(TwitterException result) {
        notifyOnFailure(result.getMessage());
    }
}
