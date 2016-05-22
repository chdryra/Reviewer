/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkStore;
import com.chdryra.android.mygenerallibrary.AsyncUtils.WorkStoreCallback;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewStore implements ReviewsRepositoryMutable.RepositoryMutableCallback, ReviewsRepository.RepositoryCallback, WorkStore<Review> {
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
    public void onRepositoryCallback(RepositoryResult result) {
        Review review = result.getReview();
        if (review == null || result.isError()) {
            mWorkStoreCallback.onFailed(review, mFetching, result.getMessage());
        } else {
            mWorkStoreCallback.onRetrievedFromStore(review, mFetching, result.getMessage());
        }
    }

    @Override
    public void onAddedToRepoCallback(RepositoryResult result) {
        Review review = result.getReview();
        ReviewId id = result.getReviewId();
        String reviewId = id != null ? id.toString() : "";
        if (result.isError() || review == null) {
            mWorkStoreCallback.onFailed(review, reviewId, result.getMessage());
        } else {
            mWorkStoreCallback.onAddedToStore(review, reviewId, result.getMessage());
        }
    }

    @Override
    public void onRemovedFromRepoCallback(RepositoryResult result) {
        ReviewId id = result.getReviewId();
        if (result.isError() || id == null) {
            mWorkStoreCallback.onFailed(null, id == null ? "" : id.toString(), result.getMessage());
        } else {
            mWorkStoreCallback.onRemovedFromStore(id.toString(), result.getMessage());
        }
    }

    @NonNull
    private DatumReviewId reviewId(String fetching) {
        return new DatumReviewId(fetching);
    }
}
