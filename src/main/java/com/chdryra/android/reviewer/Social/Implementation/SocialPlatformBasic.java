/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public abstract class SocialPlatformBasic<T> implements SocialPlatform<T> {
    private final SocialPublisher mPublisher;
    private OAuthRequester<T> mRequester;
    private T mAccessToken;

    public SocialPlatformBasic(SocialPublisher publisher) {
        mPublisher = publisher;
    }

    public SocialPlatformBasic(SocialPublisher publisher, OAuthRequester<T> requester) {
        mPublisher = publisher;
        mRequester = requester;
    }

    @Override
    public String getName() {
        return mPublisher.getPlatformName();
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
    public void setAccessToken(@Nullable T token) {
        mAccessToken = token;
    }

    @Override
    public void publish(Review review, TagsManager tagsManager, SocialPublisherListener listener) {
        mPublisher.publishAsync(review, tagsManager, listener);
    }

    @Nullable
    protected T getAccessToken() {
        return mAccessToken;
    }
}
