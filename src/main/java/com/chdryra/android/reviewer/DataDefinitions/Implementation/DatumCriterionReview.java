package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

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
