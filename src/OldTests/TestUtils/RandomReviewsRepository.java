package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.Implementation
        .ReviewNodeRepository;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.mygenerallibrary.TagsModel.Interfaces.TagsManager;

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
