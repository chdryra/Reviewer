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
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformAuthoriser;

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
    public static final PublishResults NO_AUTH_RESULT
            = new PublishResults(NAME, "No Authorisation");

    private Twitter mTwitter;
    private SocialPlatformAuthoriser<AccessToken> mAuthoriser;

    public PublisherTwitter(Twitter twitter,
                            SocialPlatformAuthoriser<AccessToken> authoriser,
                            ReviewSummariser summariser,
                            ReviewFormatter formatter) {
        super(NAME, summariser, formatter);
        mTwitter = twitter;
        mAuthoriser = authoriser;
    }

    @Override
    protected PublishResults publish(FormattedReview review, Context context) {
        if(!setUser(context)) return NO_AUTH_RESULT;

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
    public int getFollowers(Context context) {
        if(!setUser(context)) return 0;

        try {
            String screenName = mTwitter.getAccountSettings().getScreenName();
            return mTwitter.showUser(screenName).getFollowersCount();
        } catch (TwitterException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private boolean setUser(Context context) {
        AccessToken accessToken = mAuthoriser.getAuthorisationToken();
        if(accessToken != null ) {
            mTwitter.setOAuthAccessToken(accessToken);
            return true;
        } else {
            return false;
        }
    }
}
