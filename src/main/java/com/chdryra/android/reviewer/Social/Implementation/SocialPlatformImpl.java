/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public class SocialPlatformImpl<T> implements SocialPlatform<T> {
    private final SocialPublisher<T> mPublisher;
    private OAuthRequester<T> mRequester;
    private T mAccessToken;

    public SocialPlatformImpl(SocialPublisher<T> publisher) {
        mPublisher = publisher;
    }

    public SocialPlatformImpl(SocialPublisher<T> publisher, OAuthRequester<T> requester) {
        mPublisher = publisher;
        mRequester = requester;
    }

    @Override
    public String getName() {
        return mPublisher.getPlatformName();
    }

    @Override
    public void getFollowers(FollowersListener listener) {
        mPublisher.getFollowersAsync(listener);
    }

    @Override
    public SocialPublisher getPublisher() {
        return mPublisher;
    }

    @Override
    public OAuthRequester<T> getOAuthRequester() {
        return mRequester;
    }

    @Override
    public boolean isAuthorised() {
        return mAccessToken != null;
    }

    @Override
    public void setAccessToken(T token) {
        mAccessToken = token;
        mPublisher.setAccessToken(token);
    }
}
