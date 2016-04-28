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
import com.chdryra.android.reviewer.Authentication.Interfaces.TwitterLoginCallback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by: Rizwan Choudrey
 * On: 25/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class CredentialsHandlerTwitter extends CredentialsHandlerBasic<TwitterSession, TwitterLoginCallback>
        implements TwitterLoginCallback {
    public CredentialsHandlerTwitter(TwitterLogin provider, CredentialsCallback<TwitterSession> callback) {
        super(provider, callback);
    }

    @Override
    protected TwitterLoginCallback getProviderCallback() {
        return this;
    }

    @Override
    public void onSuccess(Result<TwitterSession> result) {
        notifyOnSuccess(getProviderName(), result.data);
    }

    @Override
    public void onFailure(TwitterException result) {
        String providerName = getProviderName();
        AuthenticationError error = new AuthenticationError(providerName,
                AuthenticationError.Reason.PROVIDER_ERROR, result.getMessage());
        notifyOnFailure(getProviderName(), error);
    }
}
