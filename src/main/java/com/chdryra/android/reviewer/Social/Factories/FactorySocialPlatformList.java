/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Factories;

import android.content.Context;

import com.chdryra.android.reviewer.Social.Implementation.AccessTokenDefault;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PlatformFoursquare;
import com.chdryra.android.reviewer.Social.Implementation.PlatformTumblr;
import com.chdryra.android.reviewer.Social.Implementation.PlatformTwitter;
import com.chdryra.android.reviewer.Social.Implementation.PublisherFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PublisherFourSquare;
import com.chdryra.android.reviewer.Social.Implementation.PublisherTumblr;
import com.chdryra.android.reviewer.Social.Implementation.PublisherTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterDefault;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterFacebook;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewSummariser;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;

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

    public SocialPlatform<twitter4j.auth.AccessToken> newTwitter() {
        String key = string(PlatformTwitter.CONSUMER_KEY);
        String secret = string(PlatformTwitter.CONSUMER_SECRET);

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(key).setOAuthConsumerSecret(secret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        String name = PlatformTwitter.NAME;

        ReviewSummariser summariser = new ReviewSummariser();
        ReviewFormatter formatter = new ReviewFormatterTwitter();
        PublisherTwitter publisher = new PublisherTwitter(name, twitter, summariser, formatter);

        OAuthRequester<AccessToken> authRequester = mRequesterFactory
                .newTwitterAuthorisationRequester(key, secret, name);

        return new PlatformTwitter(publisher, authRequester);
    }

    public SocialPlatform<com.facebook.AccessToken> newFacebook() {
        ReviewSummariser summariser = new ReviewSummariser();
        ReviewFormatter formatter = new ReviewFormatterFacebook();
        PublisherFacebook publisher
                = new PublisherFacebook(PlatformFacebook.NAME, summariser, formatter);

        return new PlatformFacebook(mContext, publisher);
    }

    public SocialPlatform<AccessTokenDefault> newTumblr() {
        ReviewSummariser summariser = new ReviewSummariser();
        ReviewFormatterDefault formatter = new ReviewFormatterDefault();

        String name = PlatformTumblr.NAME;
        PublisherTumblr publisher = new PublisherTumblr(name, summariser, formatter);

        String key = string(PlatformTumblr.CONSUMER_KEY);
        String secret = string(PlatformTumblr.CONSUMER_SECRET);
        OAuthRequester<AccessTokenDefault> authRequester = mRequesterFactory
                .newTumblrAuthorisationRequester(key, secret, name);

        return new PlatformTumblr(publisher, authRequester);
    }

    public SocialPlatform<AccessTokenDefault> newFourSquare() {
        ReviewSummariser summariser = new ReviewSummariser();
        ReviewFormatterDefault formatter = new ReviewFormatterDefault();

        String name = PlatformFoursquare.NAME;
        PublisherFourSquare publisher = new PublisherFourSquare(name, summariser, formatter);

        String key = string(PlatformFoursquare.CONSUMER_KEY);
        String secret = string(PlatformFoursquare.CONSUMER_SECRET);
        OAuthRequester<AccessTokenDefault> authRequester = mRequesterFactory
                .newFoursquareAuthorisationRequester(key, secret, name);

        return new PlatformFoursquare(publisher, authRequester);
    }

    private String string(int id) {
        return mContext.getString(id);
    }
}
