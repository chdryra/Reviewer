package com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataAggregator<T extends HasReviewId> {
    AggregatedList<T> aggregate(IdableList<? extends T> data);
}
