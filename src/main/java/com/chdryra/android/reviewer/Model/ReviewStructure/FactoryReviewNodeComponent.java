package com.chdryra.android.reviewer.Model.ReviewStructure;

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
        return new ReviewTreeNode(review, isAverage, review.getMdReviewId());
    }
}
