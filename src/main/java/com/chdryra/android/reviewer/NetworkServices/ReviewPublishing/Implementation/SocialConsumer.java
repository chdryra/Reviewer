/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.AsyncUtils.QueueConsumer;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.SocialConsumerListener;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPublishingListener;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactorySocialPublisher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialConsumer extends QueueConsumer<Review, SocialConsumerListener>
        implements SocialPublishingListener {
    private final FactorySocialPublisher mUploaderFactory;
    private final Map<ReviewId, ArrayList<String>> mPlatformsMap;

    public SocialConsumer(FactorySocialPublisher uploaderFactory) {
        mUploaderFactory = uploaderFactory;
        mPlatformsMap = new HashMap<>();
    }

    public void setPlatforms(ReviewId reviewId, ArrayList<String> platforms) {
        mPlatformsMap.put(reviewId, platforms);
    }

    @Override
    public void onPublishStatus(ReviewId reviewId, double percentage, PublishResults justUploaded) {
        for(SocialConsumerListener listener : getListeners()) {
            listener.onPublishingStatus(reviewId, percentage, justUploaded);
        }
    }

    @Override
    public void onPublishCompleted(ReviewId reviewId, Collection<PublishResults> publishedOk,
                                   Collection<PublishResults> publishedNotOk, CallbackMessage result) {
        onWorkCompleted(reviewId.toString());
        if(result.isError()) {
            notifyOnFailure(reviewId, result);
        } else {
            notifyOnSuccess(reviewId, publishedOk, publishedNotOk, result);
        }
    }

    private void notifyOnSuccess(ReviewId reviewId, Collection<PublishResults> publishedOk,
                                 Collection<PublishResults> publishedNotOk, CallbackMessage result) {
        for(SocialConsumerListener listener : getListeners()) {
            listener.onPublishingCompleted(reviewId, publishedOk, publishedNotOk, result);
        }
    }

    private void notifyOnFailure(ReviewId reviewId, CallbackMessage result) {
        for(SocialConsumerListener listener : getListeners()) {
            listener.onPublishingFailed(reviewId, mPlatformsMap.remove(reviewId), result);
        }
    }

    @Override
    protected void OnFailedToRetrieve(String reviewId, CallbackMessage result) {
        onWorkCompleted(reviewId);
        notifyOnFailure(new DatumReviewId(reviewId), result);
    }

    @Override
    protected ItemWorker<Review> newWorker(String reviewId) {
        DatumReviewId id = new DatumReviewId(reviewId);
        return new PublisherWorker(mUploaderFactory.newPublisher(id, mPlatformsMap.get(id)));
    }

    private static class PublisherWorker implements ItemWorker<Review> {
        private SocialPlatformsPublisher mPublisher;

        public PublisherWorker(SocialPlatformsPublisher publisher) {
            mPublisher = publisher;
        }

        @Override
        public void doWork(Review review) {
            mPublisher.publishReview();
        }
    }
}
