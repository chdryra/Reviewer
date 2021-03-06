/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Social.Interfaces.OAuthRequester;
import com.chdryra.android.startouch.Social.Interfaces.SocialPlatform;
import com.chdryra.android.startouch.Social.Interfaces.SocialPublisherAsync;
import com.chdryra.android.startouch.Social.Interfaces.SocialPublisherListener;

/**
 * Holds the name and number of followers for a social platform. Placeholder to update the
 * number of followers.
 */
public abstract class SocialPlatformBasic<T> implements SocialPlatform<T> {
    private final SocialPublisherAsync mPublisher;
    private OAuthRequester<T> mRequester;
    private T mAuthToken;
    private AuthorisationListener mAuthListener;

    SocialPlatformBasic(SocialPublisherAsync publisher) {
        mPublisher = publisher;
    }

    SocialPlatformBasic(SocialPublisherAsync publisher, OAuthRequester<T> requester) {
        mPublisher = publisher;
        mRequester = requester;
    }

    @Override
    public String getName() {
        return mPublisher.getPlatformName();
    }

    @Override
    public SocialPublisherAsync getPublisher() {
        return mPublisher;
    }

    @Override
    public OAuthRequester<T> getOAuthRequester() {
        return mRequester;
    }

    @Override
    public boolean isAuthorised() {
        return mAuthToken != null;
    }

    @Override
    public void setAuthorisation(@Nullable T token) {
        mAuthToken = token;
        if (mAuthListener != null) mAuthListener.onAuthorised(token != null);
    }

    @Override
    public void setAuthListener(AuthorisationListener authListener) {
        mAuthListener = authListener;
    }

    @Override
    public void publish(Review review, SocialPublisherListener listener) {
        mPublisher.publishAsync(review, listener);
    }

    @Nullable
    T getAccessToken() {
        return mAuthToken;
    }
}
