package com.chdryra.android.reviewer.Model.Implementation.ReviewsProviderModel;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Factories.FactoryVisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceMutable extends ReviewsSource implements ReviewsFeedMutable {
    private ReviewsRepositoryMutable mRepository;

    //Constructors
    public ReviewsSourceMutable(ReviewsRepositoryMutable repository,
                                FactoryReviewPublisher publisherFactory,
                                FactoryReviews reviewFactory,
                                FactoryVisitorReviewNode visitorFactory) {
        super(repository, publisherFactory, reviewFactory, visitorFactory);
        mRepository = repository;
    }

    @Override
    public void addReview(Review review) {
        mRepository.addReview(review);
    }

    @Override
    public void deleteReview(String reviewId) {
        mRepository.deleteReview(reviewId);
    }
}
