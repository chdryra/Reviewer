/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Social.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Social.Interfaces.ReviewFormatter;
import com.chdryra.android.startouch.Social.Interfaces.SocialPublisherAsync;
import com.chdryra.android.startouch.Social.Interfaces.SocialPublisherListener;
import com.google.android.gms.maps.model.LatLng;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import retrofit2.Call;

/**
 * Created by: Rizwan Choudrey
 * On: 10/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class PublisherTwitterFabric implements SocialPublisherAsync {
    private final String mPlatformName;
    private final ReviewSummariser mSummariser;
    private final ReviewFormatter mFormatter;

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
    public void publishAsync(Review review, SocialPublisherListener listener) {
        ReviewSummary summary = mSummariser.summarise(review);
        FormattedReview formatted = mFormatter.format(summary);
        TwitterApiClient client = TwitterCore.getInstance().getApiClient();
        StatusesService service = client.getStatusesService();
        LatLng latLng = null;
        if(review.getLocations().getDataSize().getSize() > 0) {
            latLng= review.getLocations().get(0).getLatLng();
        }

        Call<Tweet> update = service.update(formatted.getBody(),
                null,
                null,
                latLng == null ? null : latLng.latitude,
                latLng == null ? null : latLng.longitude,
                null,
                null,
                null,
                null);

        update.enqueue(newCallback(listener));
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
