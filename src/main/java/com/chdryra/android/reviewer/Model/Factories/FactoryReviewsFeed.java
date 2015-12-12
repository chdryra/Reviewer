package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewsSource;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel
        .ReviewsSourceMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Factories.FactoryReviewPublisher;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsFeed {
    public ReviewsFeed newFeed(ReviewsRepository repository,
                               FactoryReviewPublisher publisherFactory,
                               FactoryReviews reviewFactory,
                               FactoryVisitorReviewNode visitorFactory,
                               FactoryReviewTreeTraverser traverserFactory) {
        return new ReviewsSource(repository, publisherFactory, reviewFactory, visitorFactory, traverserFactory);
    }

    public ReviewsFeedMutable newMutableFeed(ReviewsRepositoryMutable repository,
                                             FactoryReviewPublisher publisherFactory,
                                             FactoryReviews reviewFactory,
                                             FactoryVisitorReviewNode visitorFactory,
                                             FactoryReviewTreeTraverser traverserFactory) {
        return new ReviewsSourceMutable(repository, publisherFactory, reviewFactory, visitorFactory, traverserFactory);
    }
}
