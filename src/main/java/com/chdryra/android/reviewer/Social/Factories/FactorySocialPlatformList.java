/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.AccessTokenDefault;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PublisherFourSquare;
import com.chdryra.android.reviewer.Social.Implementation.PublisherTumblr;
import com.chdryra.android.reviewer.Social.Implementation.PublisherTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterDefault;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewSummariser;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformImpl;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by: Rizwan Choudrey
 * On: 14/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactorySocialPlatformList {
    private static final ReviewSummariser SUMMARISER = new ReviewSummariser();
    private static final ReviewFormatterDefault FORMATTER = new ReviewFormatterDefault();
    private static final int CONSUMER_KEY_TWITTER = R.string.consumer_key_twitter;
    private static final int CONSUMER_SECRET_TWITTER = R.string.consumer_secret_twitter;
    private static final int CONSUMER_KEY_TUMBLR = R.string.consumer_key_tumblr;
    private static final int CONSUMER_SECRET_TUMBLR = R.string.consumer_secret_tumblr;
    private static final int CONSUMER_KEY_4SQUARE = R.string.consumer_key_4square;
    private static final int CONSUMER_SECRET_4SQUARE = R.string.consumer_secret_4square;

    private static SocialPlatformList sPlatforms;
    private Context mContext;
    private FactoryAuthorisationRequester mRequesterFactory;

    public FactorySocialPlatformList(Context context) {
        mContext = context;
        mRequesterFactory = new FactoryAuthorisationRequester(mContext);
    }

    public SocialPlatformList getPlatforms() {
        if(sPlatforms == null) sPlatforms = newPlatforms();
        return sPlatforms;
    }

    private SocialPlatformList newPlatforms() {
        SocialPlatformList list = new SocialPlatformList();
        list.add(newTwitter());
        list.add(newTumblr());
        list.add(newFacebook());
        list.add(newFourSquare());

        return list;
    }

    private SocialPlatform<AccessToken> newTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(string(CONSUMER_KEY_TWITTER))
                .setOAuthConsumerSecret(string(CONSUMER_SECRET_TWITTER));

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        SocialPublisher<AccessToken> publisher = new PublisherTwitter(twitter, SUMMARISER,
                new ReviewFormatterTwitter());

        return new SocialPlatformImpl<>(publisher,
                mRequesterFactory.newTwitterAuthorisationRequester(string(CONSUMER_KEY_TWITTER),
                        string(CONSUMER_SECRET_TWITTER), publisher.getPlatformName()));
    }

    public SocialPlatform<com.facebook.AccessToken> newFacebook() {
        return PlatformFacebook.getInstance(mContext);
    }


    public SocialPlatform<AccessTokenDefault> newTumblr() {
        PublisherTumblr publisher = new PublisherTumblr(SUMMARISER, FORMATTER);
        return new SocialPlatformImpl<>(publisher,
                mRequesterFactory.newTumblrAuthorisationRequester(string(CONSUMER_KEY_TUMBLR),
                        string(CONSUMER_SECRET_TUMBLR), publisher.getPlatformName()));
    }

    public SocialPlatform<AccessTokenDefault> newFourSquare() {
        PublisherFourSquare publisher = new PublisherFourSquare(SUMMARISER, FORMATTER);
        return new SocialPlatformImpl<>(publisher,
                mRequesterFactory.newFoursquareAuthorisationRequester(string(CONSUMER_KEY_4SQUARE),
                        string(CONSUMER_SECRET_4SQUARE), publisher.getPlatformName()));
    }

    private String string(int id) {
        return mContext.getString(id);
    }
}
