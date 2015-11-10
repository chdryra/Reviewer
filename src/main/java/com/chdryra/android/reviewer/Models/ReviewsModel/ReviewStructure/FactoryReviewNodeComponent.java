package com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;

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
        TreeDataGetter getter = new TreeDataGetter();
        return new ReviewTreeNode(review, isAverage, id, getter);
    }
}
