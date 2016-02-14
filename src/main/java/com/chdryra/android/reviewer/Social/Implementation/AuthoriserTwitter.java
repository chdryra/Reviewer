/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformAuthoriser;

import twitter4j.auth.AccessToken;

/**
 * Created by: Rizwan Choudrey
 * On: 14/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthoriserTwitter implements SocialPlatformAuthoriser<AccessToken>{
    private static final String RIZ_TOKEN = "697073886572212224-B9lKIZPrHvgauqStLIsYpwV6tFiO1Wm";
    private static final String RIZ_SECRET = "OuErvZFBY5CQrRbDlC40YC2Q7ijv36O8efV720b4JOkFx";
    private static final AccessToken TOKEN = new AccessToken(RIZ_TOKEN, RIZ_SECRET);

    @Override
    public AccessToken getAuthorisationToken() {
        return TOKEN;
    }
}
