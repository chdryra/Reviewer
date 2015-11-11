package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewStructure.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsProviderObserver {
    //abstract methods
    //abstract
    void onReviewAdded(Review review);

    void onReviewRemoved(String reviewId);
}
