/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Factories;

import android.content.Context;

import com.chdryra.android.startouch.Social.Implementation.AccessTokenDefault;
import com.chdryra.android.startouch.Social.Implementation.PlatformFacebook;
import com.chdryra.android.startouch.Social.Implementation.PlatformFoursquare;
import com.chdryra.android.startouch.Social.Implementation.PlatformTumblr;
import com.chdryra.android.startouch.Social.Implementation.PlatformTwitter;
import com.chdryra.android.startouch.Social.Implementation.PlatformTwitter4j;
import com.chdryra.android.startouch.Social.Implementation.PlatformTwitterFabric;
import com.chdryra.android.startouch.Social.Implementation.PublisherFacebook;
import com.chdryra.android.startouch.Social.Implementation.PublisherFourSquare;
import com.chdryra.android.startouch.Social.Implementation.PublisherTumblr;
import com.chdryra.android.startouch.Social.Implementation.PublisherTwitter4j;
import com.chdryra.android.startouch.Social.Implementation.PublisherTwitterFabric;
import com.chdryra.android.startouch.Social.Implementation.ReviewFormatterDefault;
import com.chdryra.android.startouch.Social.Implementation.ReviewFormatterFacebook;
import com.chdryra.android.startouch.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.startouch.Social.Implementation.ReviewSummariser;
import com.chdryra.android.startouch.Social.Implementation.SocialPlatformList;
import com.chdryra.android.startouch.Social.Interfaces.OAuthRequester;
import com.chdryra.android.startouch.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.startouch.Social.Interfaces.SocialPlatform;
import com.twitter.sdk.android.core.TwitterAuthToken;

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

    private final Context mContext;
    private final FactoryAuthorisationRequester mRequesterFactory;

    public FactorySocialPlatformList(Context context) {
        mContext = context;
        mRequesterFactory = new FactoryAuthorisationRequester(mContext);
    }

    public SocialPlatformList getPlatforms() {
        if (sPlatforms == null) sPlatforms = newPlatforms();
        return sPlatforms;
    }

    public SocialPlatform<TwitterAuthToken> newTwitterFabric() {
        String name = PlatformTwitter.NAME;

        ReviewSummariser summariser = new ReviewSummariser();
        ReviewFormatter formatter = new ReviewFormatterTwitter();
        PublisherTwitterFabric publisher = new PublisherTwitterFabric(name, summariser, formatter);

        return new PlatformTwitterFabric(mContext, publisher);
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

    private SocialPlatformList newPlatforms() {
        SocialPlatformList list = new SocialPlatformList();
        list.add(newTwitter4j());
        list.add(newFacebook());

        return list;
    }

    private SocialPlatform<AccessToken> newTwitter4j() {
        String key = string(PlatformTwitter.KEY);
        String secret = string(PlatformTwitter.SECRET);
        String name = PlatformTwitter.NAME;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(key).setOAuthConsumerSecret(secret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        ReviewSummariser summariser = new ReviewSummariser();
        ReviewFormatter formatter = new ReviewFormatterTwitter();
        PublisherTwitter4j publisher = new PublisherTwitter4j(name, twitter, summariser, formatter);

        return new PlatformTwitter4j(mContext, publisher);
    }

    private SocialPlatform<com.facebook.AccessToken> newFacebook() {
        ReviewSummariser summariser = new ReviewSummariser();
        ReviewFormatter formatter = new ReviewFormatterFacebook();
        PublisherFacebook publisher
                = new PublisherFacebook(PlatformFacebook.NAME, summariser, formatter);

        return new PlatformFacebook(mContext, publisher);
    }

    private String string(int id) {
        return mContext.getString(id);
    }
}
