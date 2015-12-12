package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DifferenceComparitor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorCriterion implements
        DifferenceComparitor<DataCriterion, DifferenceBoolean> {
    @Override
    public DifferenceBoolean compare(DataCriterion lhs, DataCriterion rhs) {
        boolean sameSubject = lhs.getSubject().equalsIgnoreCase(rhs.getSubject());
        boolean sameRating = lhs.getRating() == rhs.getRating();
        return new DifferenceBoolean(!(sameSubject && sameRating));
    }
}
