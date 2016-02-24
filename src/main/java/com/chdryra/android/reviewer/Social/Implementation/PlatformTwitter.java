/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.R;
import com.chdryra.android.reviewer.Social.Interfaces.OAuthRequester;

/**
 * Created by: Rizwan Choudrey
 * On: 23/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PlatformTwitter extends SocialPlatformImpl<twitter4j.auth.AccessToken>{
    public static final String NAME = "twitter";
    public static final int CONSUMER_KEY = R.string.consumer_key_twitter;
    public static final int CONSUMER_SECRET = R.string.consumer_secret_twitter;

    public PlatformTwitter(PublisherTwitter publisher,
                           OAuthRequester<twitter4j.auth.AccessToken> authRequester) {
        super(publisher, authRequester);
    }
}
