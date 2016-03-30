/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Social.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPublisherListener;


/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherGoogle implements SocialPublisher {
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
    public void publishAsync(Review review, TagsManager tagsManager,
                             final SocialPublisherListener listener) {

    }
}
