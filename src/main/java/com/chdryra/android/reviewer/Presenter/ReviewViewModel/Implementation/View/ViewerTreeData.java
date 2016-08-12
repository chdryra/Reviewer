/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Presenter.Interfaces.View.ReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Factories.FactoryReviewViewAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters
        .GvConverterReferences;

import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataRef;

/**
 * Created by: Rizwan Choudrey
 * On: 20/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ViewerTreeData<ValueType extends HasReviewId, GvRef extends
        GvDataRef<GvRef, ValueType, ?>>
        extends ViewerReviewData<ValueType, GvRef> {
    private FactoryReviewViewAdapter mAdapterFactory;

    public ViewerTreeData(ReviewListReference<ValueType> reference,
                          GvConverterReferences<ValueType, GvRef> converter,
                          FactoryReviewViewAdapter adapterFactory) {
        super(reference, converter);
        mAdapterFactory = adapterFactory;
    }

    @Override
    public boolean isExpandable(GvRef datum) {
        return getGridData().contains(datum);
    }

    @Override
    public ReviewViewAdapter<?> expandGridData() {
        return mAdapterFactory.newReviewsListAdapter(getGridData());
    }

    @Override
    public ReviewViewAdapter<?> expandGridCell(GvRef datum) {
        return isExpandable(datum) ? mAdapterFactory.newReviewsListAdapter(datum) : null;
    }

}
