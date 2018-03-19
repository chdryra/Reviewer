/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.AsyncUtils.WorkStore;
import com.chdryra.android.corelibrary.AsyncUtils.WorkStoreCallback;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Persistence.Implementation.RepoResult;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewStore implements ReviewsRepoWriteable.Callback, RepoCallback, WorkStore<Review> {
    private final ReviewsRepoWriteable mRepo;
    private WorkStoreCallback<Review> mWorkStoreCallback;
    private String mFetching;

    public ReviewStore(ReviewsRepoWriteable repo) {
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
    public void onRepoCallback(RepoResult result) {
        Review review = result.getReview();
        if (review == null || result.isError()) {
            mWorkStoreCallback.onFailed(review, mFetching, result.getMessage());
        } else {
            mWorkStoreCallback.onRetrievedFromStore(review, mFetching, result.getMessage());
        }
    }

    @Override
    public void onAddedToRepo(RepoResult result) {
        ReviewId id = result.getReviewId();
        String reviewId = id != null ? id.toString() : "";
        if (result.isReview()) {
            mWorkStoreCallback.onAddedToStore(result.getReview(), reviewId, result.getMessage());
        } else {
            mWorkStoreCallback.onFailed(result.getReview(), reviewId, result.getMessage());
        }
    }

    @Override
    public void onRemovedFromRepo(RepoResult result) {
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
