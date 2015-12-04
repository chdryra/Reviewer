package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.AggregatedCollection;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.AggregatedList;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatedCollectionImpl<T extends DataReviewIdable>
        extends IdableDataList<AggregatedList<T>> implements AggregatedCollection<T> {
    public AggregatedCollectionImpl(String reviewId) {
        super(reviewId);
    }
}
