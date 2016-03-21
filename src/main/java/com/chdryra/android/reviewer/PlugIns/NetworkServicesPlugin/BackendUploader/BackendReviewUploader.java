/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.NetworkServicesPlugin.BackendUploader;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryObserver;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewUploader implements ReviewsRepositoryObserver{
    private ArrayList<ReviewsRepositoryObserver> mObservers;
    private ReviewsRepositoryMutable mBackend;

    public BackendReviewUploader(ReviewsRepositoryMutable backend) {
        mBackend = backend;
        mObservers = new ArrayList<>();
        mBackend.registerObserver(this);
    }

    public void uploadReview(Review review) {
        mBackend.addReview(review);
    }

    public void deleteReview(ReviewId reviewId) {
        mBackend.removeReview(reviewId);
    }

    @Override
    public void onReviewAdded(Review review) {
        for(ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        for(ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewRemoved(reviewId);
        }
    }
}
