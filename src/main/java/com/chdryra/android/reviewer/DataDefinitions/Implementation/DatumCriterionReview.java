/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumCriterionReview implements DataCriterionReview {
    private ReviewId mReviewId;
    private Review mReview;

    public DatumCriterionReview(ReviewId reviewId, Review review) {
        mReviewId = reviewId;
        mReview = review;
    }

    @Override
    public String getSubject() {
        return mReview.getSubject().getSubject();
    }

    @Override
    public float getRating() {
        return mReview.getRating().getRating();
    }

    @Override
    public Review getReview() {
        return mReview;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
