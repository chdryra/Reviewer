package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces;

import com.chdryra.android.reviewer.Model.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataCriterion extends DataReview, Validatable {
    String getSubject();

    float getRating();

    Review getReview();

    @Override
    String getReviewId();
}
