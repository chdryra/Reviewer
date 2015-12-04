package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewerDbRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsRepository {
    public ReviewsRepositoryMutable newDatabaseRepository(ReviewerDb db) {
        ReviewerDbRepository repo = new ReviewerDbRepository(db);
        db.registerObserver(repo);
        return repo;
    }
}
