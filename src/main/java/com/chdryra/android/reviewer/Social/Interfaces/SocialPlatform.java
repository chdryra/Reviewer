/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSocialPlatform;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequest;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public interface SocialPlatform<T> extends DataSocialPlatform, OAuthorisable<T>{
    SocialPublisher getPublisher();

    @Override
    boolean isAuthorised();

    @Override
    void setAccessToken(T t);

    @Override
    OAuthRequest generateAuthorisationRequest();

    @Override
    T parseRequestResponse(OAuthRequest returned);

    @Override
    String getName();

    @Override
    int getFollowers();
}
