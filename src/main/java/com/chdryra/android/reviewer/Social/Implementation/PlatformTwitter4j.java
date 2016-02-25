/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;

import twitter4j.auth.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformTwitter4j extends PlatformTwitter<AccessToken> {
    public PlatformTwitter4j(Context context, PublisherTwitter4j publisher) {
        super(context, publisher);
    }

    @Override
    protected AccessToken getAccessToken() {
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        return authToken != null ? new AccessToken(authToken.token, authToken.secret) : null;
    }
}
