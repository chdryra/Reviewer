/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public abstract class SocialPlatformBasic<T> implements SocialPlatform<T> {
    private final SocialPlatformsPublisher mPublisher;
    private OAuthRequester<T> mRequester;
    private T mAccessToken;

    SocialPlatformBasic(SocialPlatformsPublisher publisher) {
        mPublisher = publisher;
    }

    SocialPlatformBasic(SocialPlatformsPublisher publisher, OAuthRequester<T> requester) {
        mPublisher = publisher;
        mRequester = requester;
    }

    @Override
    public String getName() {
        return mPublisher.getPlatformName();
    }

    @Override
    public SocialPlatformsPublisher getPublisher() {
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
    public void setAccessToken(@Nullable T token) {
        mAccessToken = token;
    }

    @Override
    public void publish(Review review, TagsManager tagsManager, SocialPublisherListener listener) {
        mPublisher.publishAsync(review, tagsManager, listener);
    }

    @Nullable
    T getAccessToken() {
        return mAccessToken;
    }
}
