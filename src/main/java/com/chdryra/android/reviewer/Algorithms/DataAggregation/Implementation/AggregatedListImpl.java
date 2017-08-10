/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Algorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.AggregatedData;
import com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 28/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatedListImpl<T extends HasReviewId>
        extends AbstractCollection<AggregatedData<T>> implements AggregatedList<T> {
    private final ReviewId mReviewId;
    private final ArrayList<AggregatedData<T>> mData;

    public AggregatedListImpl(ReviewId reviewId) {
        mReviewId = reviewId;
        mData = new ArrayList<>();
    }

    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public DataSize getDataSize() {
        return new DatumSize(getReviewId(), size());
    }

    @Override
    public AggregatedData<T> get(int position) {
        return mData.get(position);
    }


    @Override
    public boolean add(AggregatedData<T> datum) {
        return mData.add(datum);
    }


    @Override
    public Iterator<AggregatedData<T>> iterator() {
        return mData.iterator();
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
    }
}
