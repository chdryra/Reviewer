/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Factories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.chdryra.android.startouch.R;
import com.chdryra.android.startouch.Social.Implementation.AccessTokenDefault;
import com.chdryra.android.startouch.Social.Implementation.OAuthRequester10aDefault;
import com.chdryra.android.startouch.Social.Implementation.OAuthRequester20Default;
import com.chdryra.android.startouch.Social.Implementation.OAuthRequesterTwitter;
import com.chdryra.android.startouch.Social.Interfaces.OAuthRequester;
import com.github.scribejava.apis.Foursquare2Api;
import com.github.scribejava.apis.TumblrApi;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.builder.api.DefaultApi20;

import twitter4j.auth.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 22/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
class FactoryAuthorisationRequester {
    private static final int CALLBACK = R.string.callback;

    private final String mCallback;

    public FactoryAuthorisationRequester(Context context) {
        mCallback = context.getString(CALLBACK);
    }

    @NonNull
    public OAuthRequester<AccessToken> newTwitterAuthorisationRequester(String key,
                                                                        String secret,
                                                                        String platformName) {
        return new OAuthRequesterTwitter(key, secret, mCallback, TwitterApi.instance(),
                platformName);
    }

    @NonNull
    public OAuthRequester<AccessTokenDefault> newTumblrAuthorisationRequester(String key,
                                                                              String secret,
                                                                              String platformName) {
        return newDefault10aRequester(key, secret, platformName, TumblrApi.instance());
    }

    @NonNull
    public OAuthRequester<AccessTokenDefault> newFoursquareAuthorisationRequester(String key,
                                                                                  String secret,
                                                                                  String platformName) {
        return newDefault20Requester(key, secret, platformName, Foursquare2Api.instance());
    }

    @NonNull
    private OAuthRequester<AccessTokenDefault> newDefault20Requester(String key, String secret,
                                                                     String platform,
                                                                     DefaultApi20 api) {
        return new OAuthRequester20Default(key, secret, mCallback, api, platform);
    }

    @NonNull
    private OAuthRequester<AccessTokenDefault> newDefault10aRequester(String key, String secret,
                                                                      String platform,
                                                                      DefaultApi10a api) {
        return new OAuthRequester10aDefault(key, secret, mCallback, api, platform);
    }
}
