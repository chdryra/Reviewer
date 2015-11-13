package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Models.TagsModel.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewNodeRepository;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsProvider;

/**
 * Created by: Rizwan Choudrey
 * On: 16/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RandomReviewsRepository {
    private TagsManager mTagsManager;
    private FactoryReview mReviewsFactory;

    private RandomReviewsRepository(TagsManager tagsManager, FactoryReview reviewFactory) {
        mTagsManager = tagsManager;
        mReviewsFactory = reviewFactory;
    }

    public ReviewsProvider nextRepository(ReviewNode node) {
        ReviewNodeRepository provider = new ReviewNodeRepository(node, mTagsManager);
        return new ReviewsProvider(provider, mReviewsFactory, node.getAuthor());
    }
}
