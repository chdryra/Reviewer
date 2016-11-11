/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherAsync;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class SocialPublisherBasic implements SocialPublisherAsync,
        SocialPublisher {
    private final String mName;
    private final ReviewSummariser mSummariser;
    private final ReviewFormatter mFormatter;

    protected abstract PublishResults publish(FormattedReview review);

    SocialPublisherBasic(String name, ReviewSummariser summariser, ReviewFormatter formatter) {
        mName = name;
        mSummariser = summariser;
        mFormatter = formatter;
    }

    @Override
    public String getPlatformName() {
        return mName;
    }

    @Override
    public PublishResults publish(Review review) {
        ReviewSummary summary = mSummariser.summarise(review);
        FormattedReview formatted = mFormatter.format(summary);

        return publish(formatted);
    }

    @Override
    public void publishAsync(Review review, SocialPublisherListener listener) {
        new AsyncSocialPublisher(this).publish(review, listener);
    }
}
