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
import com.chdryra.android.reviewer.Social.Implementation.AuthoriserString;
import com.chdryra.android.reviewer.Social.Implementation.AuthoriserTwitter;
import com.chdryra.android.reviewer.Social.Implementation.PublisherFacebook;
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

    private static SocialPlatformList sPlatforms;
    private Context mContext;

    public FactorySocialPlatformList(Context context) {
        mContext = context;
    }

    public SocialPlatformList getPlatfomrs() {
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
                .setOAuthConsumerKey(mContext.getString(CONSUMER_KEY_TWITTER))
                .setOAuthConsumerSecret(mContext.getString(CONSUMER_SECRET_TWITTER));

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        AuthoriserTwitter authoriserTwitter = new AuthoriserTwitter();
        SocialPublisher<AccessToken> publisher = new PublisherTwitter(twitter, SUMMARISER,
                new ReviewFormatterTwitter());

        SocialPlatformImpl<AccessToken> platform = new SocialPlatformImpl<>(mContext,
                authoriserTwitter, publisher);
        platform.setAccessToken(authoriserTwitter.getAuthorisationToken());

        return platform;
    }

    public SocialPlatform<String> newFacebook() {
        return new SocialPlatformImpl<>(mContext, new AuthoriserString(),
                new PublisherFacebook(SUMMARISER, FORMATTER));
    }

    public SocialPlatform<String> newTumblr() {
        return new SocialPlatformImpl<>(mContext, new AuthoriserString(),
                new PublisherTumblr(SUMMARISER, FORMATTER));
    }

    public SocialPlatform<String> newFourSquare() {
        return new SocialPlatformImpl<>(mContext, new AuthoriserString(),
                new PublisherFourSquare(SUMMARISER, FORMATTER));
    }
}
