package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataCriterionReview extends DataCriterion {
    Review getReview();

    @Override
    String getSubject();

    @Override
    float getRating();

    @Override
    String getReviewId();

    @Override
    boolean hasData(DataValidator validator);
}
