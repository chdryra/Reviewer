package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCriterion;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvCriterion implements
        DifferenceComparitor<GvCriterion, DifferenceBoolean> {
    //Overridden
    @Override
    public DifferenceBoolean compare(GvCriterion lhs, GvCriterion
            rhs) {
        boolean sameSubject = lhs.getSubject().equalsIgnoreCase(rhs.getSubject());
        boolean sameRating = lhs.getRating() == rhs.getRating();
        return new DifferenceBoolean(!(sameSubject && sameRating));
    }
}
