package com.chdryra.android.reviewer.ReviewsProviderModel.Interfaces;

import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsProviderObserver {
    void onReviewAdded(Review review);

    void onReviewRemoved(String reviewId);
}
