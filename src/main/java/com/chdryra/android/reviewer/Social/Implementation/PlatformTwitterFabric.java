/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;

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
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        return session != null ? session.getAuthToken() : null;
    }

    @Override
    public void getFollowers(final FollowersListener listener) {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();

        Call<User> userCall = twitterApiClient.getAccountService().verifyCredentials(false,
                false, false);
        userCall.enqueue(new Callback<User>() {
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
        TwitterCore.getInstance().getSessionManager().clearActiveSession();
    }
}
