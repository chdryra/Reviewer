package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewerDbRepository;


import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewsSourceImpl;
import com.chdryra.android.reviewer.Model.Implementation.TreeMethods.Interfaces.TreeFlattener;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsSource;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsRepository {

    public ReviewsRepositoryMutable newDatabaseRepository(ReviewerDb db) {
        return new ReviewerDbRepository(db);
    }

    public ReviewsSource newReviewsSource(ReviewsRepository repository,
                                          FactoryReviews reviewsFactory,
                                          TreeFlattener flattener) {
        return new ReviewsSourceImpl(repository, reviewsFactory, flattener);
    }
}
