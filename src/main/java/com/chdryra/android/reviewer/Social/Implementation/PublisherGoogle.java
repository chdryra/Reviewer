/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.FollowersListener;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;
import com.facebook.AccessToken;


/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherGoogle implements SocialPublisher<AccessToken> {
    private String mPlatformName;
    private ReviewSummariser mSummariser;
    private ReviewFormatter mFormatter;
    private AccessToken mToken;

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
        ReviewSummary summary = mSummariser.summarise(review, tagsManager);
        FormattedReview formatted = mFormatter.format(summary);
    }

    @Override
    public void getFollowersAsync(final FollowersListener listener) {

    }

    @Override
    public void setAccessToken(AccessToken token) {
        mToken = token;
    }
}
