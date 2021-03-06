/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import com.chdryra.android.startouch.Social.Interfaces.ReviewFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherTumblr extends SocialPublisherBasic {
    public PublisherTumblr(String platformName, ReviewSummariser summariser, ReviewFormatter
            formatter) {
        super(platformName, summariser, formatter);
    }

    @Override
    protected PublishResults publish(FormattedReview review) {
        return new PublishResults(getPlatformName(), 0);
    }
}
