/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;

/**
 * Created by: Rizwan Choudrey
 * On: 12/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class SocialPublisherBasic implements SocialPlatformsPublisher,
        AsyncSocialPublisher.SyncSocialPublisher {
    private String mName;
    private ReviewSummariser mSummariser;
    private ReviewFormatter mFormatter;

    protected abstract PublishResults publish(FormattedReview review);

    public SocialPublisherBasic(String name, ReviewSummariser summariser, ReviewFormatter formatter) {
        mName = name;
        mSummariser = summariser;
        mFormatter = formatter;
    }

    @Override
    public String getPlatformName() {
        return mName;
    }

    @Override
    public PublishResults publish(Review review, TagsManager tagsManager) {
        ReviewSummary summary = mSummariser.summarise(review, tagsManager);
        FormattedReview formatted = mFormatter.format(summary);

        return publish(formatted);
    }

    @Override
    public void publishAsync(Review review, TagsManager tagsManager, SocialPublisherListener
            listener) {
        new AsyncSocialPublisher(this).publish(review, tagsManager, listener);
    }
}
