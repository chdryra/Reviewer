/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

/**
 * Created by: Rizwan Choudrey
 * On: 11/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialPlatformTwitter {
    public void getFollowers() {
        Twitter twitter = new TwitterFactory().getInstance();
    }
}
