package com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault.Implementation;


import com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Interfaces.DifferenceComparitor;

/**
 * Created by: Rizwan Choudrey
 * On: 29/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorCriterion implements DifferenceComparitor<DataCriterion, DifferenceBoolean> {
    @Override
    public DifferenceBoolean compare(DataCriterion lhs, DataCriterion rhs) {
        boolean sameSubject = lhs.getSubject().equalsIgnoreCase(rhs.getSubject());
        boolean sameRating = lhs.getRating() == rhs.getRating();
        return new DifferenceBoolean(!(sameSubject && sameRating));
    }
}
