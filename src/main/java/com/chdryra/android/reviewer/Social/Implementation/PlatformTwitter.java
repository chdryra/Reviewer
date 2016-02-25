/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import twitter4j.auth.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformTwitter extends SocialPlatformImpl<AccessToken>{
    public static final String NAME = "twitter";
    public static final int KEY = R.string.consumer_key_twitter;
    public static final int SECRET = R.string.consumer_secret_twitter;

    public PlatformTwitter(Context context, PublisherTwitter publisher,
                           OAuthRequester<AccessToken> authRequester) {
        super(publisher, authRequester);
        TwitterAuthConfig authConfig
                = new TwitterAuthConfig(context.getString(KEY), context.getString(SECRET));
        Fabric.with(context, new Twitter(authConfig));
        Fabric.with(context, new Crashlytics());
    }
}
