/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.NetworkServices.ReviewPublishing.Implementation;

import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.corelibrary.AsyncUtils.WorkStore;
import com.chdryra.android.corelibrary.AsyncUtils.WorkStoreCallback;
import com.chdryra.android.corelibrary.AsyncUtils.AsyncWorkQueueImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewQueue extends AsyncWorkQueueImpl<Review> {
    public ReviewQueue(WorkStore<Review> store) {
        super(store);
    }

    public void addReviewToQueue(Review review, WorkStoreCallback<Review> callback) {
        addItemToQueue(review, review.getReviewId().toString(), callback);
    }

    @Override
    public void addItemToQueue(Review review, String reviewId, WorkStoreCallback<Review> callback) {
        if(!reviewId.equals(review.getReviewId().toString())) {
            throw new IllegalArgumentException("ItemId should be ReviewId!");
        }

        super.addItemToQueue(review, reviewId, callback);
    }
}
