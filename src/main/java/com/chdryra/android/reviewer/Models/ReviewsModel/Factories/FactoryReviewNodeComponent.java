package com.chdryra.android.reviewer.Models.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.ReviewTreeNode;
import com.chdryra.android.reviewer.Models.ReviewsModel.Implementation.TreeDataGetterImpl;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.TreeDataGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNodeComponent {
    public ReviewNodeComponent createReviewNodeComponent(Review review, boolean isAverage) {
        return newReviewTreeNode(review, isAverage);
    }

    private ReviewTreeNode newReviewTreeNode(Review review, boolean isAverage) {
        MdReviewId id = new MdReviewId(review.getReviewId());
        TreeDataGetter getter = new TreeDataGetterImpl();
        return new ReviewTreeNode(review, isAverage, id, getter);
    }
}
