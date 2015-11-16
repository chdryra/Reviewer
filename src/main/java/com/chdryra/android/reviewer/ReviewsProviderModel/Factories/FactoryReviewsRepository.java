package com.chdryra.android.reviewer.ReviewsProviderModel.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.Implementation.ReviewerDbRepository;
import com.chdryra.android.reviewer.ReviewsProviderModel.Implementation.StaticReviewsRepository;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.TreeMethods.VisitorReviewsGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsRepository {
    public ReviewsRepository newNodeRepository(ReviewNode node, TagsManager tagsManager) {
        return newStaticRepository(VisitorReviewsGetter.flatten(node), tagsManager);
    }

    public ReviewsRepository newStaticRepository(IdableCollection<Review> reviews,
                                                 TagsManager tagsManager) {
        return new StaticReviewsRepository(reviews, tagsManager);
    }

    public ReviewsRepository newDatabaseRepository(ReviewerDb db) {
        ReviewerDbRepository repo = new ReviewerDbRepository(db);
        db.registerObserver(repo);
        return repo;
    }
}
