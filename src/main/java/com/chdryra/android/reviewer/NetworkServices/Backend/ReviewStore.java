/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.Backend;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.AsyncStore;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.AsyncStoreCallback;
import com.chdryra.android.reviewer.Utils.CallbackMessage;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewStore implements CallbackRepositoryMutable, CallbackRepository,
        AsyncStore<Review> {
    private ReviewsRepositoryMutable mRepo;
    private AsyncStoreCallback<Review> mAsyncStoreCallback;
    private String mFetching;

    public ReviewStore(ReviewsRepositoryMutable repo) {
        mRepo = repo;
    }

    @Override
    public void addItemAsync(Review item, AsyncStoreCallback<Review> callback) {
        mAsyncStoreCallback = callback;
        mRepo.addReview(item, this);
    }

    @Override
    public void getItemAsync(String itemId, AsyncStoreCallback<Review> callback) {
        mAsyncStoreCallback = callback;
        mFetching = itemId;
        mRepo.getReview(reviewId(itemId), this);
    }

    @Override
    public void removeItemAsync(String itemId, AsyncStoreCallback<Review> callback) {
        mAsyncStoreCallback = callback;
        mRepo.removeReview(reviewId(itemId), this);

    }

    @Override
    public void onAddedCallback(Review review, CallbackMessage result) {
        String reviewId = review.getReviewId().toString();
        if (result.isError()) {
            mAsyncStoreCallback.onFailed(review, reviewId, result);
        } else {
            mAsyncStoreCallback.onAddedToStore(review, reviewId, result);
        }
    }

    @Override
    public void onRemovedCallback(ReviewId reviewId, CallbackMessage result) {
        if (result.isError()) {
            mAsyncStoreCallback.onFailed(null, reviewId.toString(), result);
        } else {
            mAsyncStoreCallback.onRemovedFromStore(reviewId.toString(), result);
        }
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
        if (review == null || result.isError()) {
            mAsyncStoreCallback.onFailed(review, mFetching, result);
        } else {
            mAsyncStoreCallback.onRetrievedFromStore(review, mFetching, result);
        }
    }

    @Override
    public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {
        throw new IllegalStateException("Should never call this!");
    }

    @NonNull
    private DatumReviewId reviewId(String fetching) {
        return new DatumReviewId(fetching);
    }
}
