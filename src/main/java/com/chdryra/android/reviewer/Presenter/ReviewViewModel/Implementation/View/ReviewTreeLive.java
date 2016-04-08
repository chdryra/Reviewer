/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTreeMutableAsync;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryObserver;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeLive extends ReviewTreeMutableAsync implements CallbackRepository,
        ReviewsRepositoryObserver {

    private ReviewsRepository mRepo;
    private FactoryReviews mReviewsFactory;

    public ReviewTreeLive(ReviewsRepository repo, FactoryReviews reviewsFactory, String title) {
        super(reviewsFactory.createMetaReviewMutable(new ArrayList<Review>(), title));
        mRepo = repo;
        mReviewsFactory = reviewsFactory;
        mRepo.registerObserver(this);
        mRepo.getReviews(this);
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
        if (review != null && !result.isError()) onReviewAdded(review);
    }

    @Override
    public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {
        if (!result.isError()) {
            for (Review review : reviews) {
                addChild(review);
            }
            notifyNodeObservers();
        }
    }

    @Override
    public void onReviewAdded(Review review) {
        addChild(review);
        notifyNodeObservers();
    }

    @Override
    public void onReviewRemoved(ReviewId reviewId) {
        removeChild(reviewId);
        notifyNodeObservers();
    }

    private void addChild(Review review) {
        addChild(mReviewsFactory.createReviewNodeComponent(review, false));
    }
}
