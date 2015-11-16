package com.chdryra.android.reviewer.ReviewsProviderModel.Factories;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Factories
        .FactoryReviewPublisher;
import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.ReviewsProviderModel.Implementation.ReviewsSource;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsProvider {
    public ReviewsProvider newProvider(ReviewsRepository repository,
                                       FactoryReviewPublisher publisherFactory,
                                       FactoryReviews reviewFactory) {
        return new ReviewsSource(repository, publisherFactory, reviewFactory);
    }
}
