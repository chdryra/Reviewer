package com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceAuthoredMutable extends ReviewsSourceAuthored implements ReviewsFeedMutable {
    private ReviewsRepositoryMutable mRepository;

    public ReviewsSourceAuthoredMutable(ReviewsRepositoryMutable repository,
                                        FactoryReviews reviewFactory,
                                        TreeFlattener flattener) {
        super(repository, reviewFactory, flattener);
        mRepository = repository;
    }

    @Override
    public void addReview(Review review) {
        mRepository.addReview(review);
    }

    @Override
    public void removeReview(ReviewId reviewId) {
        mRepository.removeReview(reviewId);
    }
}
