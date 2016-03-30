/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.FollowersListener;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

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
        return session != null ? session.getAuthToken() : null;
    }

    @Override
    public void getFollowers(final FollowersListener listener) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        twitterApiClient.getAccountService().verifyCredentials(null, null, new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                listener.onNumberFollowers(result.data.followersCount);
            }

            @Override
            public void failure(TwitterException e) {
                listener.onNumberFollowers(0);
            }
        });
    }

    @Override
    public void logout() {
        Twitter.getSessionManager().clearActiveSession();
        Twitter.logOut();
    }
}
