/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.app.Activity;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class SocialPlatformPublisherBasic implements SocialPlatformPublisher {
    protected ReviewSummariser mSummariser;
    protected ReviewFormatter mFormatter;

    protected abstract PublishResults publish(FormattedReview review, Activity activity);

    public SocialPlatformPublisherBasic(ReviewSummariser summariser, ReviewFormatter formatter) {
        mSummariser = summariser;
        mFormatter = formatter;
    }

    @Override
    public PublishResults publish(Review review, TagsManager tagsManager, Activity activity) {
        ReviewSummary summary = mSummariser.summarise(review, tagsManager);
        FormattedReview formatted = mFormatter.format(summary);

        return publish(formatted, activity);
    }
}
