package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories.FactoryReviewPublisher;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsProviderModel.ReviewsSource;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsProviderModel.ReviewsSourceMutable;

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
