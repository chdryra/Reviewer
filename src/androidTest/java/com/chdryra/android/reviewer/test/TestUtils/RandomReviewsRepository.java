package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewNodeProvider;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;

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

    public ReviewsRepository nextRepository(ReviewNode node) {
        ReviewNodeProvider provider = new ReviewNodeProvider(node, mTagsManager);
        return new ReviewsRepository(provider, mReviewsFactory, node.getAuthor());
    }
}
