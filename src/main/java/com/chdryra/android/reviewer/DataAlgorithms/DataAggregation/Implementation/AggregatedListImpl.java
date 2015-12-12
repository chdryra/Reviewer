package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatedListImpl<D extends HasReviewId> implements AggregatedList<D> {
    private D mCanonical;
    private IdableList<D> mAggregated;

    public AggregatedListImpl(D canonical, IdableList<D> aggregated) {
        mCanonical = canonical;
        mAggregated = aggregated;
    }

    @Override
    public D getCanonical() {
        return mCanonical;
    }

    @Override
    public IdableList<D> getAggregated() {
        return mAggregated;
    }

    @Override
    public ReviewId getReviewId() {
        return mCanonical.getReviewId();
    }
}
