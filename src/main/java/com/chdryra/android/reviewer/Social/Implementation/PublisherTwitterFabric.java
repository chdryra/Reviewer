/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Social.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.Social.Interfaces.SocialPublisherListener;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherTwitterFabric implements SocialPlatformsPublisher {
    private String mPlatformName;
    private ReviewSummariser mSummariser;
    private ReviewFormatter mFormatter;

    public PublisherTwitterFabric(String platformName,
                                  ReviewSummariser summariser,
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
    public void publishAsync(Review review, TagsManager tagsManager, final SocialPublisherListener
            listener) {
        ReviewSummary summary = mSummariser.summarise(review, tagsManager);
        FormattedReview formatted = mFormatter.format(summary);

        TwitterApiClient client = TwitterCore.getInstance().getApiClient();
        StatusesService service = client.getStatusesService();
        service.update(formatted.getBody(), null, null, null, null, null, null, null,
                null, newCallback(listener));
    }

    @NonNull
    private Callback<Tweet> newCallback(final SocialPublisherListener listener) {
        return new Callback<Tweet>() {

            @Override
            public void success(Result<Tweet> result) {
                listener.onPublished(new PublishResults(mPlatformName, result.data.user
                        .followersCount));
            }

            @Override
            public void failure(TwitterException e) {
                PublisherTwitterFabric.this.failure(listener, e);
            }
        };
    }

    private void failure(final SocialPublisherListener listener, TwitterException e) {
        listener.onPublished(new PublishResults(mPlatformName, e.toString()));
    }
}
