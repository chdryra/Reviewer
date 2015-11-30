package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNodeComponent;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNodeComponent {
    private FactoryReviewNode mNodeFactory;

    public FactoryReviewNodeComponent() {
        mNodeFactory = new FactoryReviewNode();
    }

    public ReviewNodeComponent createReviewNodeComponent(Review review, boolean isAverage) {
        MdReviewId id = new MdReviewId(review.getReviewId());
        return new ReviewTreeNode(id, review, isAverage, mNodeFactory);
    }
}
