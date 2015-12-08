package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataAggregator<T extends DataReviewIdable> {
    AggregatedCollection<T> aggregate(IdableList<? extends T> data);
}