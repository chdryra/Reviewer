package com.chdryra.android.reviewer.Models.ReviewsProviderModel.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryReviewPublisher;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Implementation.ReviewsSource;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Implementation.ReviewsSourceMutable;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsFeed;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Models.TreeMethods.Factories.FactoryVisitorReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsFeed {
    public ReviewsFeed newFeed(ReviewsRepository repository,
                               FactoryReviewPublisher publisherFactory,
                               FactoryReviews reviewFactory,
                               FactoryVisitorReviewNode visitorFactory) {
        return new ReviewsSource(repository, publisherFactory, reviewFactory, visitorFactory);
    }

    public ReviewsFeedMutable newMtuableFeed(ReviewsRepositoryMutable repository,
                               FactoryReviewPublisher publisherFactory,
                               FactoryReviews reviewFactory,
                               FactoryVisitorReviewNode visitorFactory) {
        return new ReviewsSourceMutable(repository, publisherFactory, reviewFactory, visitorFactory);
    }
}
