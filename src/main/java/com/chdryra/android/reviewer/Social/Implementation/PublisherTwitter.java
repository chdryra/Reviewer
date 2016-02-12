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

import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherTwitter extends SocialPlatformPublisherBasic {
    private static final String NAME = "Twitter";
    private static final PublishResults SUCCESS = new PublishResults(NAME);
    private Twitter mTwitter;

    public PublisherTwitter(Twitter twitter,
                            ReviewSummariser summariser, ReviewFormatter formatter) {
        super(summariser, formatter);
        mTwitter = twitter;
    }

    @Override
    protected PublishResults publish(FormattedReview review, Activity activity) {
        PublishResults results;
        try {
            mTwitter.updateStatus(review.getBody());
            results = SUCCESS;
        } catch (TwitterException e) {
            e.printStackTrace();
            results = new PublishResults(NAME, e.getErrorMessage());
        }

        return results;
    }
}
