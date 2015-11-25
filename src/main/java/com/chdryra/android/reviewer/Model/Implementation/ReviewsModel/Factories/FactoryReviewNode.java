package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewTree;

/**
 * Created by: Rizwan Choudrey
 * On: 17/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNode {
    public ReviewNode createReviewNode(ReviewNode node) {
        return new ReviewTree(node);
    }
}
