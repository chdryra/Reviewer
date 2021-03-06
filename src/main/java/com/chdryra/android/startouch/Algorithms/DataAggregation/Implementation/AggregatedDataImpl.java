/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Algorithms.DataAggregation.Implementation;

import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.AggregatedData;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatedDataImpl<D extends HasReviewId> implements AggregatedData<D> {
    private final D mCanonical;
    private final IdableList<D> mAggregated;

    public AggregatedDataImpl(D canonical, IdableList<D> aggregated) {
        mCanonical = canonical;
        mAggregated = aggregated;
    }

    @Override
    public D getCanonical() {
        return mCanonical;
    }

    @Override
    public IdableList<D> getAggregatedItems() {
        return mAggregated;
    }

    @Override
    public ReviewId getReviewId() {
        return mCanonical.getReviewId();
    }
}
