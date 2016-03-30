/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Implementation;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.FollowersListener;
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

        if(session == null) return null;

        TwitterAuthToken authToken = session.getAuthToken();
        return authToken != null ? new AccessToken(authToken.token, authToken.secret) : null;
    }

    @Override
    public void getFollowers(FollowersListener listener) {
        FollowersFetcher.FollowersGetter getter = (FollowersFetcher.FollowersGetter)getPublisher();
        new FollowersFetcher(getter).getFollowers(listener);
    }

    @Override
    public void setAccessToken(@Nullable AccessToken token) {
        PublisherTwitter4j publisher = (PublisherTwitter4j)getPublisher();
        publisher.setAccessToken(token);
        super.setAccessToken(token);
    }

    @Override
    public void logout() {
        PublisherTwitter4j publisher = (PublisherTwitter4j)getPublisher();
        publisher.logout();
        Twitter.getSessionManager().clearActiveSession();
        Twitter.logOut();
        setAccessToken(null);
    }
}
