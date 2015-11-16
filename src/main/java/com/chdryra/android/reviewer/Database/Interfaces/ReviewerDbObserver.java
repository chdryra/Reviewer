package com.chdryra.android.reviewer.Database.Interfaces;

import com.chdryra.android.reviewer.Models.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewerDbObserver {
    //abstract
    void onReviewAdded(Review review);

    void onReviewDeleted(String reviewId);
}
