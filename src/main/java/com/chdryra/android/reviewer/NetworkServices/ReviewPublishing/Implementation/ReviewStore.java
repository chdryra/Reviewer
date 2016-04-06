/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

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
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkStore;
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkStoreCallback;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewStore implements CallbackRepositoryMutable, CallbackRepository, WorkStore<Review> {
    private ReviewsRepositoryMutable mRepo;
    private WorkStoreCallback<Review> mWorkStoreCallback;
    private String mFetching;

    public ReviewStore(ReviewsRepositoryMutable repo) {
        mRepo = repo;
    }

    @Override
    public void addItemAsync(Review item, WorkStoreCallback<Review> callback) {
        mWorkStoreCallback = callback;
        mRepo.addReview(item, this);
    }

    @Override
    public void getItemAsync(String itemId, WorkStoreCallback<Review> callback) {
        mWorkStoreCallback = callback;
        mFetching = itemId;
        mRepo.getReview(reviewId(itemId), this);
    }

    @Override
    public void removeItemAsync(String itemId, WorkStoreCallback<Review> callback) {
        mWorkStoreCallback = callback;
        mRepo.removeReview(reviewId(itemId), this);

    }

    @Override
    public void onAddedCallback(Review review, CallbackMessage result) {
        String reviewId = review.getReviewId().toString();
        if (result.isError()) {
            mWorkStoreCallback.onFailed(review, reviewId, result);
        } else {
            mWorkStoreCallback.onAddedToStore(review, reviewId, result);
        }
    }

    @Override
    public void onRemovedCallback(ReviewId reviewId, CallbackMessage result) {
        if (result.isError()) {
            mWorkStoreCallback.onFailed(null, reviewId.toString(), result);
        } else {
            mWorkStoreCallback.onRemovedFromStore(reviewId.toString(), result);
        }
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
        if (review == null || result.isError()) {
            mWorkStoreCallback.onFailed(review, mFetching, result);
        } else {
            mWorkStoreCallback.onRetrievedFromStore(review, mFetching, result);
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
