/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherTwitter extends SocialPublisherBasic {
    private static final String NAME = "twitter";
    private static final PublishResults SUCCESS = new PublishResults(NAME);
    private Twitter mTwitter;
    private User mUser;

    public PublisherTwitter(Twitter twitter,
                            ReviewSummariser summariser, ReviewFormatter formatter) {
        super(NAME, summariser, formatter);
        mTwitter = twitter;
    }

    @Override
    protected PublishResults publish(FormattedReview review, Activity activity) {
        PublishResults results;
        try {
            Status status = mTwitter.updateStatus(review.getBody());
            if(mUser == null) mUser = status.getUser();
            results = SUCCESS;
        } catch (TwitterException e) {
            e.printStackTrace();
            results = new PublishResults(NAME, e.getErrorMessage());
        }

        return results;
    }

    @Override
    public int getFollowers() {
//        if(mUser == null) setUser();
//        return mUser.getFollowersCount();
        return 0;
    }

    private void setUser() {
        try {
            String screenName = mTwitter.getAccountSettings().getScreenName();
            mUser = mTwitter.showUser(screenName);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
