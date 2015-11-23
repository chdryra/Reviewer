package com.chdryra.android.reviewer.Models.ReviewsProviderModel.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Implementation.ReviewerDbRepository;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Implementation.StaticReviewsRepository;
import com.chdryra.android.reviewer.Models.ReviewsProviderModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Models.TreeMethods.Factories.FactoryVisitorReviewNode;
import com.chdryra.android.reviewer.Models.TreeMethods.Implementation.VisitorReviewsGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsRepository {
    FactoryVisitorReviewNode mVisitorFactory;

    public FactoryReviewsRepository(FactoryVisitorReviewNode visitorFactory) {
        mVisitorFactory = visitorFactory;
    }

    public ReviewsRepository newNodeRepository(ReviewNode node, TagsManager tagsManager) {
        VisitorReviewsGetter getter = mVisitorFactory.newReviewsGetter();
        getter.visit(node);
        return newStaticRepository(getter.getReviews(), tagsManager);
    }

    public ReviewsRepository newStaticRepository(IdableCollection<Review> reviews,
                                                 TagsManager tagsManager) {
        return new StaticReviewsRepository(reviews, tagsManager);
    }

    public ReviewsRepositoryMutable newDatabaseRepository(ReviewerDb db) {
        ReviewerDbRepository repo = new ReviewerDbRepository(db);
        db.registerObserver(repo);
        return repo;
    }
}