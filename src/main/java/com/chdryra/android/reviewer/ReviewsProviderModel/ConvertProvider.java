package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewPublisher;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.UserData.Author;

/**
 * Created by: Rizwan Choudrey
 * On: 01/10/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConvertProvider {
    public static ReviewNode getReviewNode(ReviewsProvider provider, Author nodeAuthor, String title) {
        ReviewPublisher publisher = new ReviewPublisher(nodeAuthor, PublishDate.now());
        Review root = FactoryReview.createReviewUser(publisher, title, 0f);
        ReviewTreeNode node = FactoryReview.createReviewTreeNode(root, true);
        for(Review review : provider.getReviews()) {
            ReviewTreeNode child = FactoryReview.createReviewTreeNode(review, true);
            node.addChild(child);
        }
        return node;
    }
}
