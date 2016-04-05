/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.NetworkServices.ReviewPublishing.Implementation;

import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.WorkStore;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.WorkStoreCallback;
import com.chdryra.android.reviewer.NetworkServices.WorkQueueModel.AsyncWorkQueueImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 05/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewQueue extends AsyncWorkQueueImpl<Review> {
    public ReviewQueue(WorkStore<Review> store) {
        super(store);
    }

    public void addForWork(Review review, WorkStoreCallback<Review> callback) {
        addItemToQueue(review, review.getReviewId().toString(), callback);
    }

    @Override
    public void addItemToQueue(Review item, String itemId, WorkStoreCallback<Review> callback) {
        if(!itemId.equals(item.getReviewId().toString())) {
            throw new IllegalArgumentException("ItemId should be ReviewId!");
        }

        super.addItemToQueue(item, itemId, callback);
    }
}
