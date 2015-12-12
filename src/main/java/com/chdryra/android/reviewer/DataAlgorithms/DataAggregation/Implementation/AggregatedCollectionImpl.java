package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.AggregatedCollection;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatedCollectionImpl<T extends HasReviewId>
        extends IdableDataList<AggregatedList<T>> implements AggregatedCollection<T> {
    public AggregatedCollectionImpl(ReviewId reviewId) {
        super(reviewId);
    }
}
