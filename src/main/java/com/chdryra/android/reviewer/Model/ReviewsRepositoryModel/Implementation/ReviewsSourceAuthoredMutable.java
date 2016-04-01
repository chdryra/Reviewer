/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceAuthoredMutable extends ReviewsSourceAuthored implements ReviewsFeedMutable {
    private ReviewsRepositoryMutable mRepository;

    public ReviewsSourceAuthoredMutable(ReviewsRepositoryMutable repository,
                                        FactoryReviews reviewFactory) {
        super(repository, reviewFactory);
        mRepository = repository;
    }

    @Override
    public void addReview(Review review, CallbackRepositoryMutable callback) {
        mRepository.addReview(review, callback);
    }

    @Override
    public void removeReview(ReviewId reviewId, CallbackRepositoryMutable callback) {
        mRepository.removeReview(reviewId, callback);
    }
}
