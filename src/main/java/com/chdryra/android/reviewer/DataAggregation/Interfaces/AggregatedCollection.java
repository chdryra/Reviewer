package com.chdryra.android.reviewer.DataAggregation.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface AggregatedCollection<T extends DataReviewIdable>
        extends IdableList<AggregatedList<T>> {
}
