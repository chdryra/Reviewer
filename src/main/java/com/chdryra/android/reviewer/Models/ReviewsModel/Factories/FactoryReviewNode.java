package com.chdryra.android.reviewer.Models.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNode {
    private FactoryReviewNodeComponent mComponentFactory;

    public FactoryReviewNode(FactoryReviewNodeComponent componentFactory) {
        mComponentFactory = componentFactory;
    }

    public ReviewNode createReviewNode(Review review) {
        return createReviewNode(mComponentFactory.createReviewNodeComponent(review, false));
    }

    public ReviewNode createReviewNode(ReviewNode node) {
        return new ReviewTree(node);
    }
}
