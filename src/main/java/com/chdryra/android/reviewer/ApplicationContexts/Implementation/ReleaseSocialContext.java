/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.SocialContext;
import com.chdryra.android.reviewer.Social.Implementation.PublisherFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PublisherFourSquare;
import com.chdryra.android.reviewer.Social.Implementation.PublisherTumblr;
import com.chdryra.android.reviewer.Social.Implementation.PublisherTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterDefault;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewSummariser;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleaseSocialContext implements SocialContext {
    public static final ReviewSummariser SUMMARISER = new ReviewSummariser();
    public static final ReviewFormatterDefault FORMATTER = new ReviewFormatterDefault();
    private SocialPlatformList mPlatforms;

    public ReleaseSocialContext() {
        mPlatforms = new SocialPlatformList();
        mPlatforms.add(newTwitterPublisher());
        mPlatforms.add(newFacebookPublisher());
        mPlatforms.add(newTumblrPublisher());
        mPlatforms.add(newFourSquarePublisher());
    }

    @Override
    public SocialPlatformList getSocialPlatforms() {
        return mPlatforms;
    }

    private SocialPublisher newTwitterPublisher() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("S1cZ1sRDt4pbUP49MCduMt0IQ")
                .setOAuthConsumerSecret("mUhFe14zIdh1E3wg9kPrad2hsk6rHHlquZ2eFXjK1p5I3EmqsX")
                .setOAuthAccessToken("697073886572212224-B9lKIZPrHvgauqStLIsYpwV6tFiO1Wm")
                .setOAuthAccessTokenSecret("OuErvZFBY5CQrRbDlC40YC2Q7ijv36O8efV720b4JOkFx");

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        return new PublisherTwitter(twitter, SUMMARISER, new ReviewFormatterTwitter());
    }

    private SocialPublisher newFacebookPublisher() {
        return new PublisherFacebook(SUMMARISER, FORMATTER);
    }

    private SocialPublisher newTumblrPublisher() {
        return new PublisherTumblr(SUMMARISER, FORMATTER);
    }

    private SocialPublisher newFourSquarePublisher() {
        return new PublisherFourSquare(SUMMARISER, FORMATTER);
    }
}
