package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewTreeComponent;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNode {
    public ReviewNodeComponent createReviewNodeComponent(Review review, boolean isAverage) {
        MdReviewId id = new MdReviewId(review.getReviewId());
        return new ReviewTreeComponent(id, review, isAverage);
    }

    public ReviewNode createReviewNode(Review review, boolean isAverage) {
        return createReviewNode(createReviewNodeComponent(review, isAverage));
    }

    public ReviewNode createReviewNode(ReviewNodeComponent node) {
        return new ReviewTree(node);
    }
}
