/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.support.annotation.NonNull;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.Token;

/**
 * Created by: Rizwan Choudrey
 * On: 15/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class OAuthRequester10aDefault extends OAuthRequester10a<AccessTokenDefault> {

    public OAuthRequester10aDefault(String consumerKey, String consumerSecret,
                                    String callBack, DefaultApi10a api, String platformName) {
        super(consumerKey, consumerSecret, callBack, api, platformName);
    }

    @NonNull
    @Override
    protected AccessTokenDefault newAccessToken(Token accessToken) {
        return new AccessTokenDefault(accessToken.getToken(), accessToken.getSecret());
    }
}
