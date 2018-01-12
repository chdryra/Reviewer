/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.content.Context;
import android.util.Log;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Interfaces.AuthorisationTokenGetter;
import com.chdryra.android.reviewer.Social.Interfaces.LoginUi;
import com.chdryra.android.reviewer.Social.Interfaces.PlatformAuthoriser;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherAsync;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by: Rizwan Choudrey
 * On: 25/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PlatformTwitter<T> extends SocialPlatformBasic<T> {
    public static final String NAME = "twitter";
    public static final int KEY = R.string.consumer_key_twitter;
    public static final int SECRET = R.string.consumer_secret_twitter;

    PlatformTwitter(Context context, SocialPublisherAsync publisher) {
        super(publisher);
        initialiseTwitter(context);
        setAuthorisation(getAccessToken());
    }

    private void initialiseTwitter(Context context) {
        TwitterConfig config = new TwitterConfig.Builder(context)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(context.getString(KEY),
                        context.getString(SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }

    @Override
    public LoginUi getLoginUi(LaunchableUi loginLaunchable, PlatformAuthoriser.Callback listener) {
        return new LoginUiDefault<>(loginLaunchable, this,
                listener, new AuthorisationTokenGetter<T>() {
            @Override
            public T getAuthorisationToken() {
                return PlatformTwitter.this.getAccessToken();
            }
        });
    }
}
