/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.startouch.Social.Interfaces.SocialPublisherAsync;
import com.chdryra.android.startouch.Social.Interfaces.SocialPublisherListener;


/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherGoogle implements SocialPublisherAsync {
    private String mPlatformName;
    private ReviewSummariser mSummariser;
    private ReviewFormatter mFormatter;

    public PublisherGoogle(String platformName, ReviewSummariser summariser,
                           ReviewFormatter formatter) {
        mPlatformName = platformName;
        mSummariser = summariser;
        mFormatter = formatter;
    }

    @Override
    public String getPlatformName() {
        return mPlatformName;
    }

    @Override
    public void publishAsync(Review review, SocialPublisherListener listener) {

    }
}
