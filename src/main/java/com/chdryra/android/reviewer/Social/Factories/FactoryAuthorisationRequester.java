/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Social.Implementation.AccessTokenDefault;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequesterDefault;
import com.chdryra.android.reviewer.Social.Implementation.OAuthRequesterTwitter;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;
import com.github.scribejava.apis.FoursquareApi;
import com.github.scribejava.apis.TumblrApi;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.DefaultApi10a;

import twitter4j.auth.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 22/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryAuthorisationRequester {
    @NonNull
    public OAuthRequester<AccessToken> newTwitterAuthorisationRequester(String key,
                                                                           String secret,
                                                                           String callBack,
                                                                           String platformName) {
        return new OAuthRequesterTwitter(key, secret, callBack, TwitterApi.instance(), platformName);
    }

    @NonNull
    public OAuthRequester<AccessTokenDefault> newTumblrAuthorisationRequester(String key,
                                                                          String secret,
                                                                          String callBack,
                                                                          String platformName) {
        return newDefaultRequester(key, secret, callBack, platformName, TumblrApi.instance());
    }

    @NonNull
    public OAuthRequester<AccessTokenDefault> newFacebookAuthorisationRequester(String key,
                                                                              String secret,
                                                                              String callBack,
                                                                              String platformName) {
        return newDefaultRequester(key, secret, callBack, platformName, TumblrApi.instance());
    }

    @NonNull
    public OAuthRequester<AccessTokenDefault> newFoursquareAuthorisationRequester(String key,
                                                                                String secret,
                                                                                String callBack,
                                                                                String platformName) {
        return newDefaultRequester(key, secret, callBack, platformName, FoursquareApi.instance());
    }

    @NonNull
    private OAuthRequester<AccessTokenDefault> newDefaultRequester(String key, String secret,
                                                                   String callBack, String platform,
                                                                   DefaultApi10a api) {
        return new OAuthRequesterDefault(key, secret, callBack, api, platform);
    }

}
