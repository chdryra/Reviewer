package com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewTreeTraverser;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceMutable extends ReviewsSource implements ReviewsFeedMutable {
    private ReviewsRepositoryMutable mRepository;

    //Constructors
    public ReviewsSourceMutable(ReviewsRepositoryMutable repository,
                                FactoryReviews reviewFactory,
                                FactoryVisitorReviewNode visitorFactory,
                                FactoryReviewTreeTraverser traverserFactory) {
        super(repository, reviewFactory, visitorFactory, traverserFactory);
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
