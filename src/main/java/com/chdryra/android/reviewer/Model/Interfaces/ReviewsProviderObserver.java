package com.chdryra.android.reviewer.Model.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsProviderObserver {
    void onReviewAdded(Review review);

    void onReviewRemoved(String reviewId);
}
