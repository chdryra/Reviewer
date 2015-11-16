package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Models.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Models.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.Implementation.ReviewNodeRepository;
import com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces.ReviewsProvider;

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

    public ReviewsProvider nextRepository(ReviewNode node) {
        ReviewNodeRepository provider = new ReviewNodeRepository(node, mTagsManager);
        return new ReviewsProvider(provider, mReviewsFactory, node.getAuthor());
    }
}
