/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Implementation;

import android.app.Activity;
import android.content.Context;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.AuthorisationListener;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.AuthorisationTokenGetter;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.LoginUi;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.View.LauncherModel.Interfaces.LaunchableUi;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by: Rizwan Choudrey
 * On: 25/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class PlatformTwitter<T> extends SocialPlatformBasic<T> {
    public static final String NAME = "twitter";
    public static final int KEY = R.string.consumer_key_twitter;
    public static final int SECRET = R.string.consumer_secret_twitter;

    public PlatformTwitter(Context context, SocialPublisher publisher) {
        super(publisher);
        TwitterAuthConfig authConfig
                = new TwitterAuthConfig(context.getString(KEY), context.getString(SECRET));
        Fabric.with(context, new Twitter(authConfig));
        Fabric.with(context, new Crashlytics());
        setAccessToken(getAccessToken());
    }

    @Override
    public LoginUi getLoginUi(Activity activity, LaunchableUi loginLaunchable,
                                      AuthorisationListener listener) {
        return new LoginUiDefault<>(activity, loginLaunchable, this,
                listener, new AuthorisationTokenGetter<T>() {
            @Override
            public T getAuthorisationToken() {
                return PlatformTwitter.this.getAccessToken();
            }
        });
    }
}
