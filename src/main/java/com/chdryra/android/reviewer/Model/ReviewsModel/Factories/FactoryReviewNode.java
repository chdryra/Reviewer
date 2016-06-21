/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.NodeLeaf;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNode {
    public ReviewNodeComponent createReviewNodeComponent(Review review, boolean isAverage) {
        return new NodeLeaf(review, isAverage);
    }

    public ReviewNode createReviewNode(Review review, boolean isAverage) {
        return createReviewNode(createReviewNodeComponent(review, isAverage));
    }

    public ReviewNode createReviewNode(ReviewNodeComponent node) {
        return new ReviewTree(node);
    }
}
