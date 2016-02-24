/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherTwitter extends SocialPublisherBasic<AccessToken>
        implements FollowersFetcher.FollowersGetter, AsyncSocialPublisher.SyncSocialPublisher{
    private static final String NAME = "twitter";
    public static final PublishResults NO_AUTH_RESULT
            = new PublishResults(NAME, "No Authorisation");

    private Twitter mTwitter;
    private AccessToken mToken;

    public PublisherTwitter(Twitter twitter,
                            ReviewSummariser summariser,
                            ReviewFormatter formatter) {
        super(NAME, summariser, formatter);
        mTwitter = twitter;
    }

    @Override
    protected PublishResults publish(FormattedReview review) {
        if(mToken == null) return NO_AUTH_RESULT;

        PublishResults results;
        try {
            Status status = mTwitter.updateStatus(review.getBody());
            results = new PublishResults(NAME, status.getUser().getFollowersCount());
        } catch (TwitterException e) {
            e.printStackTrace();
            results = new PublishResults(NAME, e.getErrorMessage());
        }

        return results;
    }

    @Override
    public void getFollowersAsync(FollowersListener listener) {
        new FollowersFetcher(this).getFollowers(listener);
    }

    @Override
    public int getFollowers() {
        if(mToken == null) return 0;

        try {
            String screenName = mTwitter.getAccountSettings().getScreenName();
            return mTwitter.showUser(screenName).getFollowersCount();
        } catch (TwitterException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void setAccessToken(AccessToken token) {
        mToken = token;
        mTwitter.setOAuthAccessToken(mToken);
    }
}
