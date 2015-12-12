package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface AggregatedList<D extends HasReviewId> extends HasReviewId {
    D getCanonical();

    IdableList<D> getAggregated();

    @Override
    ReviewId getReviewId();
}
