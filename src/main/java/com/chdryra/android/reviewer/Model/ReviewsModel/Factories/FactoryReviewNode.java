/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTreeMutable;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNode {
    public ReviewNodeMutable createReviewNodeComponent(Review review, boolean isAverage) {
        return new ReviewTreeMutable(review, isAverage);
    }

    public ReviewNode createReviewNode(Review review, boolean isAverage) {
        return createReviewNode(createReviewNodeComponent(review, isAverage));
    }

    public ReviewNode createReviewNode(ReviewNodeMutable node) {
        return new ReviewTree(node);
    }
}
