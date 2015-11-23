package com.chdryra.android.reviewer.Models.ReviewsProviderModel.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Models.TreeMethods.Factories.FactoryVisitorReviewNode;

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
