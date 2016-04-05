/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Interfaces
        .SocialConsumerListener;
import com.chdryra.android.reviewer.NetworkServices.Social.Implementation.PublishResults;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPlatformsPublisher;
import com.chdryra.android.reviewer.NetworkServices.Social.Interfaces.SocialPublishingListener;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.AsyncWorkQueue;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.WorkerToken;
import com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.Api.FactorySocialPublisher;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SocialConsumer implements SocialPublishingListener, AsyncWorkQueue.QueueObserver{
    private ReviewQueue mQueue;
    private FactorySocialPublisher mUploaderFactory;
    private Map<ReviewId, SocialPlatformsPublisher> mUploaders;
    private ArrayList<ReviewId> mUploading;
    private ArrayList<SocialConsumerListener> mListeners;
    private Map<String, WorkerToken> mTokens;
    private Map<ReviewId, ArrayList<String>> mPlatformsMap;

    public SocialConsumer(FactorySocialPublisher uploaderFactory) {
        mUploaderFactory = uploaderFactory;
        mUploaders = new HashMap<>();
        mUploading = new ArrayList<>();
        mListeners = new ArrayList<>();
        mTokens = new HashMap<>();
        mPlatformsMap = new HashMap<>();
    }

    public void setQueue(ReviewQueue queue) {
        if(mQueue != null) throw new IllegalStateException("Cannot reset Queue!");

        mQueue = queue;
        mQueue.registerObserver(this);
    }

    public void registerListener(SocialConsumerListener listener) {
        if (!mListeners.contains(listener)) mListeners.add(listener);
    }

    public void unregisterListener(SocialConsumerListener listener) {
        if (mListeners.contains(listener)) mListeners.remove(listener);
    }

    public void setPlatforms(ReviewId reviewId, ArrayList<String> platforms) {
        mPlatformsMap.put(reviewId, platforms);
    }

    @Override
    public void onPublishStatus(ReviewId reviewId, double percentage, PublishResults justUploaded) {

    }

    @Override
    public void onPublishCompleted(ReviewId reviewId, Collection<PublishResults> publishedOk,
                                   Collection<PublishResults> publishedNotOk, CallbackMessage result) {
        mUploading.remove(reviewId);
        if (result.isError()) {
            notifyListenersOnFail(reviewId, result);
            return;
        }

        notifyListenersOnSuccess(reviewId, publishedOk, publishedNotOk, result);
        mUploaders.remove(reviewId);
        mQueue.workComplete(mTokens.get(reviewId.toString()));
    }

    @Override
    public void onAddedToQueue(String itemId) {
        mTokens.put(itemId, mQueue.addWorker(itemId, this));
        publish(reviewId(itemId));
    }

    private void publish(ReviewId reviewId) {
        if (!mUploading.contains(reviewId)) {
            mUploading.add(reviewId);
            getUploader(reviewId).publishReview();
        }
    }

    @NonNull
    private DatumReviewId reviewId(String itemId) {
        return new DatumReviewId(itemId);
    }

    private void notifyListenersOnFail(ReviewId reviewId, CallbackMessage result) {
        for (SocialConsumerListener listener : mListeners) {
            listener.onPublishingFailed(reviewId, mPlatformsMap.get(reviewId), result);
        }
    }

    private void notifyListenersOnSuccess(ReviewId reviewId, Collection<PublishResults> publishedOk,
                                          Collection<PublishResults> publishedNotOk, CallbackMessage result) {
        for (SocialConsumerListener listener : mListeners) {
            listener.onPublishingCompleted(reviewId, publishedOk, publishedNotOk, result);
        }
    }

    @NonNull
    private SocialPlatformsPublisher getUploader(ReviewId reviewId) {
        if (!mUploaders.containsKey(reviewId)) {
            SocialPlatformsPublisher uploader = mUploaderFactory.newPublisher(reviewId, mPlatformsMap.get(reviewId));
            uploader.registerListener(this);
            mUploaders.put(reviewId, uploader);
        }

        return mUploaders.get(reviewId);
    }
}
