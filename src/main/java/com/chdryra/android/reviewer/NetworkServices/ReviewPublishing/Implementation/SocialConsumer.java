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
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces.SocialUploader;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.NetworkServicesPlugin.Api.FactorySocialPublisher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 01/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialConsumer extends QueueConsumer<Review> implements SocialUploader.Listener {
    private final FactorySocialPublisher mPublisherFactory;
    private final Map<ReviewId, ArrayList<String>> mPlatformsMap;
    private final ArrayList<SocialUploader.Listener> mListeners;

    public SocialConsumer(FactorySocialPublisher publisherFactory) {
        mPublisherFactory = publisherFactory;
        mPlatformsMap = new HashMap<>();
        mListeners = new ArrayList<>();
    }

    public void setPlatforms(ReviewId reviewId, ArrayList<String> platforms) {
        mPlatformsMap.put(reviewId, platforms);
    }

    public void unsetPlatforms(ReviewId reviewId) {
        mPlatformsMap.remove(reviewId);
    }

    public void registerListener(SocialUploader.Listener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(SocialUploader.Listener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    @Override
    public void onPublishingFailed(ReviewId reviewId, Collection<String> platforms, CallbackMessage result) {
        onWorkCompleted(reviewId.toString());
        notifyOnFailure(reviewId, result);
    }

    @Override
    public void onPublishingCompleted(ReviewId reviewId, Collection<PublishResults> publishedOk,
                                      Collection<PublishResults> publishedNotOk, CallbackMessage
                                                  result) {
        onWorkCompleted(reviewId.toString());
        notifyOnSuccess(reviewId, publishedOk, publishedNotOk, result);
    }

    @Override
    public void onPublishingStatus(ReviewId reviewId, double percentage, PublishResults justUploaded) {
        for (SocialUploader.Listener listener : mListeners) {
            listener.onPublishingStatus(reviewId, percentage, justUploaded);
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
        return new PublisherWorker(mPublisherFactory.newPublisher(id, mPlatformsMap.get(id)));
    }

    private void notifyOnSuccess(ReviewId reviewId, Collection<PublishResults> publishedOk,
                                 Collection<PublishResults> publishedNotOk, CallbackMessage
                                         result) {
        for (SocialUploader.Listener listener : mListeners) {
            listener.onPublishingCompleted(reviewId, publishedOk, publishedNotOk, result);
        }
    }

    private void notifyOnFailure(ReviewId reviewId, CallbackMessage result) {
        for (SocialUploader.Listener listener : mListeners) {
            listener.onPublishingFailed(reviewId, mPlatformsMap.remove(reviewId), result);
        }
    }

    private static class PublisherWorker implements ItemWorker<Review> {
        private final SocialUploader mPublisher;

        public PublisherWorker(SocialUploader publisher) {
            mPublisher = publisher;
        }

        @Override
        public void doWork(Review review) {
            mPublisher.uploadReview();
        }
    }
}
