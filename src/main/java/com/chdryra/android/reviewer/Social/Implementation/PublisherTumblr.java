/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherTumblr extends SocialPublisherBasic {
    private static final String NAME = "tumblr";
    private static final PublishResults SUCCESS = new PublishResults(NAME, 0);

    public PublisherTumblr(ReviewSummariser summariser, ReviewFormatter formatter) {
        super(NAME, summariser, formatter);
    }

    @Override
    protected PublishResults publish(FormattedReview review, Activity activity) {
        return SUCCESS;
    }

    @Override
    public int getFollowers() {
        return 0;
    }
}
