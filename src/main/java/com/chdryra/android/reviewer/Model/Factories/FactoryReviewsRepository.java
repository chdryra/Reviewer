package com.chdryra.android.reviewer.Model.Factories;

import com.chdryra.android.reviewer.Database.Interfaces.ReviewerPersistence;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewerPersistenceRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsRepository {
    public ReviewsRepositoryMutable newPersistentRepository(ReviewerPersistence db) {
        ReviewerPersistenceRepository repo = new ReviewerPersistenceRepository(db);
        db.registerObserver(repo);
        return repo;
    }
}
