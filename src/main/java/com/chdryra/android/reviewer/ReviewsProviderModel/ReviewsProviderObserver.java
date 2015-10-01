package com.chdryra.android.reviewer.ReviewsProviderModel;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 30/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ReviewsProviderObserver {
    void onReviewAdded(Review review);

    void onReviewRemoved(ReviewId id);
}
