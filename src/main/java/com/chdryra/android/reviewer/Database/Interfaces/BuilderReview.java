package com.chdryra.android.reviewer.Database.Interfaces;

import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface BuilderReview {
    Review createReview(ReviewDataHolder review);
}
