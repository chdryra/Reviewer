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
    private ControllerReviewCollection<Review> mController;

    public PublishedReviews() {
        mController = new ControllerReviewCollection<>(new RCollectionReview<Review>());
    }

    public GvDataList toGridViewable() {
        return mController.toGridViewable(true);
    }

    public void add(Review review) {
        mController.addReview(review);
    }
}
