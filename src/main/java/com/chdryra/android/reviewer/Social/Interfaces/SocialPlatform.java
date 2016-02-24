/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public interface SocialPlatform<T> extends DataSocialPlatform, OAuthorisable<T>{
    SocialPublisher getPublisher();

    OAuthRequester<T> getOAuthRequester();

    @Override
    boolean isAuthorised();

    @Override
    void setAccessToken(T t);

    @Override
    String getName();

    @Override
    void getFollowers(FollowersListener listener);
}
