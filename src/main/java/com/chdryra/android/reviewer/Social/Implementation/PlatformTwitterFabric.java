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

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformTwitterFabric extends PlatformTwitter<TwitterAuthToken> {
    public PlatformTwitterFabric(Context context, PublisherTwitterFabric publisher) {
        super(context, publisher);
    }

    @Override
    protected TwitterAuthToken getAccessToken() {
        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        return session.getAuthToken();
    }
}
