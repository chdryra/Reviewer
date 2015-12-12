package com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsRepositoryObserver {
    void onReviewAdded(Review review);

    void onReviewRemoved(ReviewId reviewId);
}
