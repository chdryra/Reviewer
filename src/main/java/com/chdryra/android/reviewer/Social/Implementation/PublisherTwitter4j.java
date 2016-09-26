/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherTwitter4j extends SocialPublisherBasic implements
        FollowersFetcher.FollowersGetter,
        SocialPublisher {

    private final Twitter mTwitter;
    private final String mPlatformName;

    public PublisherTwitter4j(String platformName, Twitter twitter,
                              ReviewSummariser summariser,
                              ReviewFormatter formatter) {
        super(platformName, summariser, formatter);
        mTwitter = twitter;
        mPlatformName = platformName;
    }

    @Override
    protected PublishResults publish(FormattedReview review) {
        PublishResults results;
        try {
            Status status = mTwitter.updateStatus(review.getBody());
            results = new PublishResults(mPlatformName, status.getUser().getFollowersCount());
        } catch (TwitterException e) {
            e.printStackTrace();
            results = new PublishResults(mPlatformName, e.getErrorMessage());
        }

        return results;
    }

    @Override
    public int getFollowers() {
        try {
            String screenName = mTwitter.getAccountSettings().getScreenName();
            return mTwitter.showUser(screenName).getFollowersCount();
        } catch (TwitterException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setAccessToken(@Nullable AccessToken token) {
        mTwitter.setOAuthAccessToken(token);
    }

    public void logout() {
        mTwitter.setOAuthAccessToken(null);
    }
}
