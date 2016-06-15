/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTreeMutableAsync;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 08/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewTreeLive extends ReviewTreeMutableAsync implements ReviewsRepository.RepositoryCallback,
        ReviewsRepositoryObserver {

    private ReviewsRepository mRepo;
    private FactoryReviews mReviewsFactory;

    public ReviewTreeLive(DataAuthor author, ReviewsRepository repo, FactoryReviews reviewsFactory, String title) {
        super(reviewsFactory.createMetaReviewMutable(new ArrayList<Review>(), title));
        mRepo = repo;
        mReviewsFactory = reviewsFactory;
        mRepo.registerObserver(this);
        mRepo.getReferences(author, this);
    }


    @Override
    public void onRepositoryCallback(RepositoryResult result) {
        if (!result.isError()) {
            for (ReviewReference review : result.getReferences()) {
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

    private void addChild(ReviewReference review) {
        addChild(mReviewsFactory.createReviewNodeComponent(review, false));
    }

    public void detachFromRepo() {
        mRepo.unregisterObserver(this);
    }
}
