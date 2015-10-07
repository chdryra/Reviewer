package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorGvCriterion implements
        DifferenceComparitor<GvCriterionList.GvCriterion, DifferenceBoolean> {
    //Overridden
    @Override
    public DifferenceBoolean compare(GvCriterionList.GvCriterion lhs, GvCriterionList.GvCriterion
            rhs) {
        boolean sameSubject = lhs.getSubject().equalsIgnoreCase(rhs.getSubject());
        boolean sameRating = lhs.getRating() == rhs.getRating();
        return new DifferenceBoolean(!(sameSubject && sameRating));
    }
}
