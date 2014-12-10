/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 2 December, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Created by: Rizwan Choudrey
 * On: 02/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class PublishedReviews {
    private ControllerReviewCollection<ReviewNode> mController;

    PublishedReviews() {
        mController = new ControllerReviewCollection<>(new RCollectionReview<ReviewNode>());
    }

    public VgDataList toGridViewable() {
        return mController.toGridViewable(true);
    }

    void add(ReviewNode review) {
        if (review.isPublished()) mController.addReview(review);
    }
}
