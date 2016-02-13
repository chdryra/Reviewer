/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;

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
public class PublisherTwitter extends SocialPublisherBasic {
    private static final String NAME = "twitter";
    private static final String RIZ_TOKEN = "697073886572212224-B9lKIZPrHvgauqStLIsYpwV6tFiO1Wm";
    private static final String RIZ_SECRET = "OuErvZFBY5CQrRbDlC40YC2Q7ijv36O8efV720b4JOkFx";
    
    private AccessToken mToken = new AccessToken(RIZ_TOKEN, RIZ_SECRET);
    private Twitter mTwitter;

    public PublisherTwitter(Twitter twitter,
                            ReviewSummariser summariser, ReviewFormatter formatter) {
        super(NAME, summariser, formatter);
        mTwitter = twitter;
    }

    @Override
    protected PublishResults publish(FormattedReview review, Context context) {
        setUser(context);

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

    private AccessToken getAccessToken(Context context) {
        return mToken;
    }

    @Override
    public int getFollowers(Context context) {
        setUser(context);
        try {
            String screenName = mTwitter.getAccountSettings().getScreenName();
            return mTwitter.showUser(screenName).getFollowersCount();
        } catch (TwitterException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void setUser(Context context) {
        mTwitter.setOAuthAccessToken(getAccessToken(context));
    }
}
