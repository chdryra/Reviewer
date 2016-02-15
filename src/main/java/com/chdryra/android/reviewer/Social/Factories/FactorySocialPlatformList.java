/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Factories;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Implementation.AccessTokenDefault;
import com.chdryra.android.reviewer.Social.Implementation.AuthorisationRequesterDefault;
import com.chdryra.android.reviewer.Social.Implementation.PublisherFacebook;
import com.chdryra.android.reviewer.Social.Implementation.PublisherFourSquare;
import com.chdryra.android.reviewer.Social.Implementation.PublisherTumblr;
import com.chdryra.android.reviewer.Social.Implementation.PublisherTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterDefault;
import com.chdryra.android.reviewer.Social.Implementation.ReviewFormatterTwitter;
import com.chdryra.android.reviewer.Social.Implementation.ReviewSummariser;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformImpl;
import com.chdryra.android.reviewer.Social.Implementation.SocialPlatformList;
import com.chdryra.android.reviewer.Social.Implementation.TwitterToken;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatform;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.github.scribejava.apis.TumblrApi;
import com.github.scribejava.core.builder.api.DefaultApi10a;

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
    public static final int CONSUMER_KEY_TUMBLR = R.string.consumer_key_tumblr;
    public static final int CONSUMER_SECRET_TUMBLR = R.string.consumer_secret_tumblr;

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
        SocialPublisher<AccessToken> publisher = new PublisherTwitter(twitter, SUMMARISER,
                new ReviewFormatterTwitter());

        SocialPlatform<AccessToken> platform = new SocialPlatformImpl<>(mContext, publisher, null);

        platform.setAccessToken(TwitterToken.getToken());

        return platform;
    }

    public SocialPlatform<AccessTokenDefault> newFacebook() {
        PublisherFacebook publisher = new PublisherFacebook(SUMMARISER, FORMATTER);
        return new SocialPlatformImpl<>(mContext, publisher, getAuthorisationRequester
                (CONSUMER_KEY_TUMBLR, CONSUMER_SECRET_TUMBLR,
                publisher.getName(), TumblrApi.instance()));
    }

    public SocialPlatform<AccessTokenDefault> newTumblr() {
        PublisherTumblr publisher = new PublisherTumblr(SUMMARISER, FORMATTER);
        return new SocialPlatformImpl<>(mContext, publisher,
                getAuthorisationRequester(CONSUMER_KEY_TUMBLR, CONSUMER_SECRET_TUMBLR,
                        publisher.getName(), TumblrApi.instance()));
    }

    @NonNull
    private AuthorisationRequesterDefault getAuthorisationRequester(int key, int secret,
                                                                    String platform,
                                                                    DefaultApi10a api) {
        Resources resources = mContext.getResources();
        return new AuthorisationRequesterDefault(resources.getString(key),
                resources.getString(secret), resources.getString(R.string.callback),
                api, platform);
    }

    public SocialPlatform<AccessTokenDefault> newFourSquare() {
        PublisherFourSquare publisher = new PublisherFourSquare(SUMMARISER, FORMATTER);
        return new SocialPlatformImpl<>(mContext, publisher, getAuthorisationRequester
                (CONSUMER_KEY_TUMBLR, CONSUMER_SECRET_TUMBLR,
                        publisher.getName(), TumblrApi.instance()));
    }
}
