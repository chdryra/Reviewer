package com.chdryra.android.reviewer.DataDefinitions.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataCriterion extends HasReviewId, Validatable {
    String getSubject();

    float getRating();

    @Override
    ReviewId getReviewId();
}
