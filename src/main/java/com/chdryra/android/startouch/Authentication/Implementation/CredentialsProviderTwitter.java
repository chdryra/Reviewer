/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Authentication.Implementation;

import com.chdryra.android.startouch.Authentication.Interfaces.LoginTwitter;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CredentialsProviderTwitter extends CredentialsProviderBasic<TwitterSession, LoginTwitter.Callback>
        implements LoginTwitter.Callback {

    public CredentialsProviderTwitter(LoginTwitter provider) {
        super(provider);
    }

    @Override
    protected LoginTwitter.Callback getProviderCallback() {
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
