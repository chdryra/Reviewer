package com.chdryra.android.reviewer.ApplicationSingletons;

import android.content.Context;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;

/**
 * Created by: Rizwan Choudrey
 * On: 31/08/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsManager extends ApplicationSingleton {
    private static final String NAME = "ReviewManager";
    private static ReviewsManager sSingleton;

    public ReviewsManager(Context context) {
        super(context, NAME);
    }

    public static ReviewsManager get(Context c) {
        sSingleton = getSingleton(sSingleton, ReviewsManager.class, c);
        return sSingleton;
    }

    public static ReviewNode getReviewNode(Context context, String reviewId) {
        ReviewsManager manager = get(context);
        ReviewNode root = manager.getFeedNode();
        return manager.findNode(root, reviewId);
    }

    private ReviewNode getFeedNode() {
        return ReviewFeed.getFeedNode(getContext());
    }

    private ReviewNode findNode(ReviewNode root, String reviewId) {
        ReviewId id = ReviewId.fromString(reviewId);
        ReviewNode node = null;
        if (root.getId().equals(id)) {
            node = root;
        } else {
            for (ReviewNode child : root.getChildren()) {
                node = findNode(child, reviewId);
                if (node != null) {
                    break;
                } else {
                    node = findNode(child.getReview().getTreeRepresentation(), reviewId);
                    if (node != null) break;
                }
            }
        }

        return node;
    }
}
