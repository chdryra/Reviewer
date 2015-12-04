package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.Implementation.ReviewNodeRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeed;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomReviewsRepository {
    private TagsManager mTagsManager;
    private FactoryReviews mReviewsFactory;

    private RandomReviewsRepository(TagsManager tagsManager, FactoryReviews reviewFactory) {
        mTagsManager = tagsManager;
        mReviewsFactory = reviewFactory;
    }

    public ReviewsFeed nextRepository(ReviewNode node) {
        ReviewNodeRepository provider = new ReviewNodeRepository(node, mTagsManager);
        return new ReviewsFeed(provider, mReviewsFactory, node.getAuthor());
    }
}
